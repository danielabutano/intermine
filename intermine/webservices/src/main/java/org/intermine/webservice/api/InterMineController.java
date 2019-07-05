package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.util.PropertiesUtil;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.JSONModel;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.StatusDictionary;
import org.intermine.webservice.server.WebServiceConstants;
import org.intermine.webservice.server.WebServiceRequestParser;
import org.intermine.webservice.server.exceptions.NotAcceptableException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

public class InterMineController {

    protected final ObjectMapper objectMapper;

    protected final HttpServletRequest request;

    protected HttpHeaders httpHeaders;

    protected HttpStatus httpStatus;

    private static final Logger LOG = Logger.getLogger(InterMineController.class);

    private static final String COMPRESS = "compress";
    private static final String GZIP = "gzip";
    private static final String ZIP = "zip";


    /**
     * Constants for property keys in global property configuration.
     */
    private static final String WS_HEADERS_PREFIX = "ws.response.header";

    protected Properties webProperties;

    protected String errorMessage;
    protected int status;

    protected Format format;
    private Boolean isJsonP;

    public InterMineController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public void initController(){
        httpHeaders = new HttpHeaders();
        httpStatus = HttpStatus.OK;
        status = HttpStatus.OK.value();
        errorMessage = null;
        format = null;
        isJsonP = null;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHeaders() {
        webProperties = InterMineContext.getWebProperties();
        Properties headerProps = PropertiesUtil.getPropertiesStartingWith(
                WS_HEADERS_PREFIX, webProperties);


        for (Object o : headerProps.values()) {
            String h = o.toString();
            String[] parts = StringUtils.split(h, ":", 2);
            if (parts.length != 2) {
                LOG.warn("Ignoring invalid response header: " + h);
            } else {
                httpHeaders.set(parts[0].trim(), parts[1].trim());
            }
        }

        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            httpHeaders.setAccessControlAllowOrigin(origin);
        }

        String filename = getRequestFileName();
        switch (getFormat()) {
            case HTML:
                ResponseUtilSpring.setHTMLContentType(httpHeaders);
                break;
            case XML:
                filename += ".xml";
                ResponseUtilSpring.setXMLHeader(httpHeaders, filename);
                break;
            case TSV:
                filename += ".tsv";
                if (isUncompressed()) {
                    ResponseUtilSpring.setTabHeader(httpHeaders, filename);
                }
                break;
            case CSV:
                filename += ".csv";
                if (isUncompressed()) {
                    ResponseUtilSpring.setCSVHeader(httpHeaders, filename);
                }
                break;
            case TEXT:
                filename += getExtension();
                if (isUncompressed()) {
                    ResponseUtilSpring.setPlainTextHeader(httpHeaders, filename);
                }
                break;
            case JSON:
                filename += ".json";
                if (isUncompressed()) {
                    ResponseUtilSpring.setJSONHeader(httpHeaders, filename, formatIsJSONP());
                }
                break;
            case OBJECTS:
                filename += ".json";
                if (isUncompressed()) {
                    ResponseUtilSpring.setJSONHeader(httpHeaders, filename, formatIsJSONP());
                }
                break;
            case TABLE:
                filename = "resulttable.json";
                if (isUncompressed()) {
                    ResponseUtilSpring.setJSONHeader(httpHeaders, filename, formatIsJSONP());
                }
                break;
            case ROWS:
                if (isUncompressed()) {
                    ResponseUtilSpring.setJSONHeader(httpHeaders, "result.json", formatIsJSONP());
                }
                break;
            default:
        }
        if (!isUncompressed()) {
            ResponseUtilSpring.setGzippedHeader(httpHeaders, filename + getExtension());
        }
    }
    /**
     * If the request has a <code>filename</code> parameter then use that
     * for the fileName, otherwise use the default fileName
     * @return the fileName to use for the exported file
     */
    protected String getRequestFileName() {
        String param = WebServiceRequestParser.FILENAME_PARAMETER;
        String fileName = request.getParameter(param);
        if (StringUtils.isBlank(fileName)) {
            return getDefaultFileName();
        } else {
            return fileName.trim();
        }
    }

    /**
     * @return The default file name for this service. (default = "result.tsv")
     */
    protected String getDefaultFileName() {
        return "result";
    }


    /**
     * @return The default format constant for this service.
     */
    protected Format getDefaultFormat() {
        return Format.EMPTY;
    }

    /**
     * Returns required output format.
     *
     * Cannot be overridden.
     *
     * @return format
     */
    public final Format getFormat() {
        if (format == null) {
            List<Format> askedFor = WebServiceRequestParser.getAcceptableFormats(request);
            if (askedFor.isEmpty()) {
                format = getDefaultFormat();
            } else {
                for (Format acceptable: askedFor) {
                    if (Format.DEFAULT == acceptable) {
                        format = getDefaultFormat();
                        break;
                    }
                    // Serve the first acceptable format.
                    if (canServe(acceptable)) {
                        format = acceptable;
                        break;
                    }
                }
                // Nothing --> NotAcceptable
                if (format == null) {
                    throw new NotAcceptableException();
                }
                // But empty --> default
                if (format == Format.EMPTY) {
                    format = getDefaultFormat();
                }
            }
        }

        return format;
    }

    /**
     * Check whether the format is acceptable.
     *
     * By default returns true. Services with a particular set of accepted
     * formats should override this and check.
     * @param format The format to check.
     * @return whether or not this format is acceptable.
     */
    protected boolean canServe(Format format) {
        return format == getDefaultFormat();
    }


    /**
     * @return Whether or not this request wants uncompressed data.
     */
    protected boolean isUncompressed() {
        return StringUtils.isEmpty(request.getParameter(COMPRESS));
    }

    /**
     * @return the file-name extension for the result-set.
     */
    protected String getExtension() {
        if (isGzip()) {
            return ".gz";
        } else if (isZip()) {
            return ".zip";
        } else {
            return "";
        }
    }

    /**
     * @return Whether or not this request wants gzipped data.
     */
    protected boolean isGzip() {
        return GZIP.equalsIgnoreCase(request.getParameter(COMPRESS));
    }

    /**
     * @return Whether or not this request wants zipped data.
     */
    protected boolean isZip() {
        return ZIP.equalsIgnoreCase(request.getParameter(COMPRESS));
    }

    /**
     * @return Whether or not the format is a JSON-P format
     */
    protected final boolean formatIsJSONP() {
        if (isJsonP == null) {
            isJsonP = WebServiceRequestParser.isJsonP(request);
        }
        return isJsonP;
    }

    /**
     * Set the executionTime, wasSuccessful, error and statusCode of the respective response model
     * Standard procedure is to override this method in subclasses for respective model classes
     */
    public void setFooter(JSONModel jsonModel){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        jsonModel.setExecutionTime(executionTime);


        if (status >= 400) {
            jsonModel.setWasSuccessful(false);
            jsonModel.setError(escapeJava(errorMessage));
        } else {
            jsonModel.setWasSuccessful(true);
        }
        jsonModel.setStatusCode(status);
    }

    protected void sendError(Throwable t) {

        webProperties = InterMineContext.getWebProperties();
        errorMessage = WebServiceConstants.SERVICE_FAILED_MSG;
        boolean showAllMsgs = webProperties.containsKey("i.am.a.dev");

        if (t instanceof ServiceException) {
            status = ((ServiceException) t).getHttpErrorCode();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        String realMsg = t.getMessage();
        if ((showAllMsgs || status < 500) && !StringUtils.isBlank(realMsg)) {
            errorMessage = realMsg;
        }
        logError(t, realMsg, status);
        //if (!formatIsJSONP()) {
            // Don't set errors statuses on jsonp requests, to enable
            // better error checking in the browser.
            httpStatus = HttpStatus.valueOf(status);
        //}

    }

    private void logError(Throwable t, String msg, int code) {

        // Stack traces for all!
        String truncatedStackTrace = getTruncatedStackTrace(t);
        if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            LOG.error("Service failed by internal error. Request parameters: \n"
                    + requestParametersToString() + t + "\n" + truncatedStackTrace);
        } else {
            LOG.debug("Service didn't succeed. It's not an internal error. "
                    + "Reason: " + getErrorDescription(msg, code) + "\n"
                    + truncatedStackTrace);
        }
    }

    private String getTruncatedStackTrace(Throwable t) {
        StackTraceElement[] stack = t.getStackTrace();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b);
        boolean tooDeep = false;

        for (int i = 0; !tooDeep && i < stack.length; i++) {
            StackTraceElement element = stack[i];
            if (element.getClassName().contains("catalina")) {
                // We have descended as far as is useful. stop here.
                tooDeep = true;
                ps.print("\n ...");
            } else {
                ps.print("\n  at ");
                ps.print(element);
            }
        }
        if (t.getCause() != null) {
            ps.print("\n caused by: " + t.getCause() + "\n" + getTruncatedStackTrace(t.getCause()));
        }
        ps.flush();
        return b.toString();
    }


    private String requestParametersToString() {
        StringBuilder sb = new StringBuilder();
        @SuppressWarnings("unchecked") // Old pre-generic API.
                Map<String, String[]> map = request.getParameterMap();
        for (String name : map.keySet()) {
            for (String value : map.get(name)) {
                sb.append(name);
                sb.append(": ");
                sb.append(value);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String getErrorDescription(String msg, int errorCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(StatusDictionary.getDescription(errorCode));
        sb.append(" ");
        sb.append(msg);
        return sb.toString();
    }
}

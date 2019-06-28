package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.webservice.model.JSONModel;
import org.intermine.webservice.server.StatusDictionary;
import org.intermine.webservice.server.WebServiceConstants;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

public class InterMineController {

    protected final ObjectMapper objectMapper;

    protected final HttpServletRequest request;

    protected HttpHeaders httpHeaders;

    protected HttpStatus httpStatus;

    private static final Logger LOG = Logger.getLogger(InterMineController.class);

    protected Properties webProperties;

    protected String errorMessage = null;
    protected int status = HttpStatus.OK.value();

    public InterMineController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        //httpHeaders = new HttpHeaders();
        httpStatus = HttpStatus.OK;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

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

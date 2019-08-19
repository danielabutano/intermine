package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.GeneratedCode;
import org.intermine.webservice.model.SavedQueries;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.query.CodeService;
import org.intermine.webservice.server.query.QueryUploadService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class QueryApiController extends InterMineController implements QueryApi {

    private static final Logger log = LoggerFactory.getLogger(QueryApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public QueryApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<?> generatedCodeGet(@NotNull @ApiParam(value = "The language to generate code in.", required = true, allowableValues = "pl, py, rb, js, java") @Valid @RequestParam(value = "lang", required = true, defaultValue = "py") String lang,@NotNull @ApiParam(value = "The query to generate code for, in XML or JSON form.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "", allowableValues = "text, xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        GeneratedCode generatedCode = serve();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "querycode.json");
            setFooter(generatedCode);
            return new ResponseEntity<GeneratedCode>(generatedCode, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(generatedCode.getCode(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> generatedCodePost(@NotNull @ApiParam(value = "The language to generate code in.", required = true, allowableValues = "pl, py, rb, js, java") @Valid @RequestParam(value = "lang", required = true, defaultValue = "py") String lang,@NotNull @ApiParam(value = "The query to generate code for, in XML or JSON form.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "", allowableValues = "text, xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        GeneratedCode generatedCode = serve();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "querycode.json");
            setFooter(generatedCode);
            return new ResponseEntity<GeneratedCode>(generatedCode, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(generatedCode.getCode(),httpHeaders,httpStatus);
    }

    private GeneratedCode serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        CodeService codeService = new CodeService(im,httpHeaders, format);
        try {
            codeService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        return codeService.getGeneratedCode();
    }


    public ResponseEntity<?> queryUploadGet(@NotNull @ApiParam(value = "A definition of the query/ies to save in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query, @ApiParam(value = "The version of the path-qeury format being used.") @Valid @RequestParam(value = "version", required = false, defaultValue = "2") Integer version, @ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveQueryUpload(format, "");
    }

    public ResponseEntity<?> queryUploadPost(@NotNull @ApiParam(value = "A definition of the query/ies to save in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The version of the path-qeury format being used.") @Valid @RequestParam(value = "version", required = false, defaultValue = "2") Integer version,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveQueryUpload(format, "");
    }

    private ResponseEntity<?> serveQueryUpload(String format, String body){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QueryUploadService queryUploadService = new QueryUploadService(im, getFormat(), body);
        try {
            queryUploadService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SavedQueries savedQueries = queryUploadService.getSavedQueries();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "uploaded-queries" + ".json", false);
            setFooter(savedQueries);
            return new ResponseEntity<SavedQueries>(savedQueries, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, "uploaded-queries" + ".xml");
        return new ResponseEntity<Object>(savedQueries.getQueries(),httpHeaders,httpStatus);
    }

    @Override
    protected String getDefaultFileName() {
        return "query";
    }

    @Override
    protected boolean canServe(Format format) {
        switch (format) {
            case JSON:
                return true;
            case TEXT:
                return true;
            default:
                return false;
        }
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.TEXT;
    }


}

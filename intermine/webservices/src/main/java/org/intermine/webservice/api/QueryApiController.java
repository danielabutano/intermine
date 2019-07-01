package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.GeneratedCode;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.query.CodeService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        GeneratedCode generatedCode = serve();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "querycode.json");
            setFooter(generatedCode);
            return new ResponseEntity<GeneratedCode>(generatedCode, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(generatedCode.getCode(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> generatedCodePost(@NotNull @ApiParam(value = "The language to generate code in.", required = true, allowableValues = "pl, py, rb, js, java") @Valid @RequestParam(value = "lang", required = true, defaultValue = "py") String lang,@NotNull @ApiParam(value = "The query to generate code for, in XML or JSON form.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "", allowableValues = "text, xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
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

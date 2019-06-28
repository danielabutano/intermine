package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SummaryFields;
import org.intermine.webservice.server.SummaryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class SummaryFieldsApiController extends InterMineController implements SummaryFieldsApi {

    private static final Logger LOG = Logger.getLogger(ModelApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public SummaryFieldsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<SummaryFields> summaryfields(@ApiParam(value = "Whether to exclude references from the summary fields") @Valid @RequestParam(value = "norefs", required = false) Boolean norefs) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        SummaryService summaryService = new SummaryService(im);
        try {
            summaryService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SummaryFields summaryFields = summaryService.getSummaryFields();
        setFooter(summaryFields);
        httpHeaders = summaryService.getResponseHeaders();

        return new ResponseEntity<SummaryFields>(summaryFields,httpHeaders,getHttpStatus());
    }

}

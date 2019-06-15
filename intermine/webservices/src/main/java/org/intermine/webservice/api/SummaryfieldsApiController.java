package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Summaryfields;
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
public class SummaryfieldsApiController implements SummaryfieldsApi {

    private static final Logger LOG = Logger.getLogger(ModelApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;

    @org.springframework.beans.factory.annotation.Autowired
    public SummaryfieldsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Summaryfields> summaryfields(@ApiParam(value = "Whether to exclude references from the summary fields") @Valid @RequestParam(value = "norefs", required = false) Boolean norefs) {
        String accept = request.getHeader("Accept");
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        SummaryService summaryService = new SummaryService(im);
        summaryService.service(request);
        Summaryfields summaryfields = summaryService.getSummaryfields();
        httpHeaders = summaryService.getResponseHeaders();
        httpStatus = summaryService.getHttpStatus();
        summaryService.setFooter();

        return new ResponseEntity<Summaryfields>(summaryfields,httpHeaders,httpStatus);
    }

}

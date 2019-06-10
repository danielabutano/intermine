package org.intermine.webservicesspring.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservicesspring.model.Model;
//import org.intermine.webservicesspring.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservicesspring.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class ModelApiController implements ModelApi {

    private static final Logger log = LoggerFactory.getLogger(ModelApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    /*
    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;
    */


    @Autowired
    public ModelApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Model> model(@ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false) String format) {
        String accept = request.getHeader("Accept");
        /*modelService.service(request);
        Model model = modelService.getResponseModel();
        httpHeaders = modelService.getResponseHeaders();
        httpStatus = modelService.getHttpStatus();*/
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        return new ResponseEntity<Model>(HttpStatus.NOT_IMPLEMENTED);
    }

}

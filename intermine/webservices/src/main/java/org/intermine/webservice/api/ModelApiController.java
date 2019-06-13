package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Model;
import org.intermine.webservice.server.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class ModelApiController implements ModelApi {

    private static final Logger LOG = Logger.getLogger(ModelApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;




    @Autowired
    public ModelApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> model(@ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format, HttpServletResponse response) {
        String accept = request.getHeader("Accept");

        final InterMineAPI im = InterMineContext.getInterMineAPI();

        ModelService modelService = new ModelService(im);
        modelService.service(request,response);
        Model model = modelService.getResponseModel();
        httpHeaders = modelService.getResponseHeaders();
        httpStatus = modelService.getHttpStatus();
        if(format.equals("json")) {
            modelService.setFooter();
            return new ResponseEntity<Model>(model, httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(model.getModel(),httpHeaders,HttpStatus.OK);
    }

}

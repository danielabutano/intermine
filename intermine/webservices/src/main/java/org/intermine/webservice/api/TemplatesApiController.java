package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.TemplatesSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.template.SystemTemplatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T01:19:48.599+05:30[Asia/Kolkata]")
@Controller
public class TemplatesApiController implements TemplatesApi {

    private static final Logger log = LoggerFactory.getLogger(TemplatesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;


    @org.springframework.beans.factory.annotation.Autowired
    public TemplatesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> templatesSystem(@ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format) {
        String accept = request.getHeader("Accept");

        final InterMineAPI im = InterMineContext.getInterMineAPI();

        SystemTemplatesService systemTemplatesService = new SystemTemplatesService(im);
        systemTemplatesService.service(request);
        TemplatesSystem templatesSystem = systemTemplatesService.getTemplatesSystem();
        httpHeaders = systemTemplatesService.getResponseHeaders();
        httpStatus = systemTemplatesService.getHttpStatus();
        if(format.equals("json")) {
            systemTemplatesService.setFooter();
            return new ResponseEntity<TemplatesSystem>(templatesSystem, httpHeaders, httpStatus);
        }
        return new ResponseEntity<Object>(templatesSystem.getTemplates(),httpHeaders,httpStatus);
    }

}

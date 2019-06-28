package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.WebProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.webproperties.WebPropertiesService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@Controller
public class WebPropertiesApiController implements WebPropertiesApi {

    private static final Logger log = LoggerFactory.getLogger(WebPropertiesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;

    @org.springframework.beans.factory.annotation.Autowired
    public WebPropertiesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<WebProperties> webProperties() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        WebPropertiesService webPropertiesService = new WebPropertiesService(im);
        try {
            webPropertiesService.service(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        webPropertiesService.setFooter();
        WebProperties webProperties = webPropertiesService.getWebPropertiesModel();
        httpHeaders = webPropertiesService.getResponseHeaders();
        httpStatus = webPropertiesService.getHttpStatus();

        return new ResponseEntity<WebProperties>(webProperties,httpHeaders,httpStatus);
    }

}

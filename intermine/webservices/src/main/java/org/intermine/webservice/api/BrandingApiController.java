package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Branding;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.branding.BrandingService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-17T02:38:26.046+05:30[Asia/Kolkata]")
@Controller
public class BrandingApiController extends InterMineController implements BrandingApi {

    private static final Logger log = LoggerFactory.getLogger(BrandingApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public BrandingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Branding> branding() {
        webProperties = InterMineContext.getWebProperties();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        BrandingService brandingService = new BrandingService(im);
        try {
            brandingService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Branding branding = brandingService.getBranding();
        setFooter(branding);
        httpHeaders = brandingService.getResponseHeaders();

        return new ResponseEntity<Branding>(branding,httpHeaders,getHttpStatus());
    }

}

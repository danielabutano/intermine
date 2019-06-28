package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.PermanentUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.fair.PermanentURLService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class PermanentUrlApiController extends InterMineController implements PermanentUrlApi {

    private static final Logger log = LoggerFactory.getLogger(PermanentUrlApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public PermanentUrlApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<PermanentUrl> permanentUrl(@NotNull @ApiParam(value = "The type of the entity.", required = true) @Valid @RequestParam(value = "type", required = true) String type,@NotNull @ApiParam(value = "The internal intermine ID.", required = true) @Valid @RequestParam(value = "id", required = true) String id) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        PermanentURLService permanentURLService = new PermanentURLService(im);
        try {
            permanentURLService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        PermanentUrl permanentUrl = permanentURLService.getPermanentUrl();
        setFooter(permanentUrl);
        httpHeaders = permanentURLService.getResponseHeaders();

        return new ResponseEntity<PermanentUrl>(permanentUrl,httpHeaders,getHttpStatus());
    }

}

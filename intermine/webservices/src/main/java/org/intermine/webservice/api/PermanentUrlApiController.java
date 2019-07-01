package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.PermanentUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.fair.PermanentURLService;
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
public class PermanentUrlApiController extends InterMineController implements PermanentUrlApi {

    private static final Logger log = LoggerFactory.getLogger(PermanentUrlApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public PermanentUrlApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<PermanentUrl> permanentUrl(@NotNull @ApiParam(value = "The type of the entity.", required = true) @Valid @RequestParam(value = "type", required = true) String type,@NotNull @ApiParam(value = "The internal intermine ID.", required = true) @Valid @RequestParam(value = "id", required = true) String id) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        PermanentURLService permanentURLService = new PermanentURLService(im, format);
        try {
            permanentURLService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        PermanentUrl permanentUrl = permanentURLService.getPermanentUrl();
        setFooter(permanentUrl);

        return new ResponseEntity<PermanentUrl>(permanentUrl,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Branding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.branding.BrandingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-17T02:38:26.046+05:30[Asia/Kolkata]")
@Controller
public class BrandingApiController extends InterMineController implements BrandingApi {

    private static final Logger log = LoggerFactory.getLogger(BrandingApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public BrandingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Branding> branding() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        BrandingService brandingService = new BrandingService(im, format);
        try {
            brandingService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Branding branding = brandingService.getBranding();
        setFooter(branding);

        return new ResponseEntity<Branding>(branding,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

}

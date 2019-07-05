package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.WebProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.webproperties.WebPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@Controller
public class WebPropertiesApiController extends InterMineController implements WebPropertiesApi {

    private static final Logger log = LoggerFactory.getLogger(WebPropertiesApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public WebPropertiesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<WebProperties> webProperties() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        WebPropertiesService webPropertiesService = new WebPropertiesService(im, format);
        try {
            webPropertiesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        WebProperties webProperties = webPropertiesService.getWebPropertiesModel();
        setFooter(webProperties);

        return new ResponseEntity<WebProperties>(webProperties,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

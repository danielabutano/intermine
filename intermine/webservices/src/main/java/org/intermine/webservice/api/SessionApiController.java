package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@Controller
public class SessionApiController extends InterMineController implements SessionApi {

    private static final Logger log = LoggerFactory.getLogger(SessionApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public SessionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Session> session() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SessionService sessionService = new SessionService(im, format);
        try {
            sessionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Session session = sessionService.getSession();
        setFooter(session);

        return new ResponseEntity<Session>(session,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

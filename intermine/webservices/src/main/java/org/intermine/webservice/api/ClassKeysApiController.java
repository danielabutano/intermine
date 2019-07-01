package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SummaryFields;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intermine.webservice.server.ClassKeysService;
import org.intermine.webservice.server.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-17T02:38:26.046+05:30[Asia/Kolkata]")
@Controller
public class ClassKeysApiController extends InterMineController implements ClassKeysApi {

    private static final Logger log = LoggerFactory.getLogger(ClassKeysApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public ClassKeysApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<SummaryFields> classkeys() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ClassKeysService classKeysService = new ClassKeysService(im, format);
        try {
            classKeysService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SummaryFields keyFields = classKeysService.getSummaryFields();
        setFooter(keyFields);

        return new ResponseEntity<SummaryFields>(keyFields,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

}

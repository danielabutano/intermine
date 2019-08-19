package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.JBrowseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.data.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class DataApiController extends InterMineController implements DataApi {

    private static final Logger log = LoggerFactory.getLogger(DataApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public DataApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<JBrowseData> jBrowseSimpleData(@ApiParam(value = "The type of the object to find.",required=true) @PathVariable("type") String type,@ApiParam(value = "The range of values requested." ) @RequestHeader(value="Range", required=false) String range,@ApiParam(value = "An optional filter over the objects.") @Valid @RequestParam(value = "filter", required = false) List<String> filter) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DataService dataService = new DataService(im,type, format);
        try {
            dataService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        JBrowseData jBrowseData = dataService.getjBrowseData();
        setFooter(jBrowseData);

        return new ResponseEntity<JBrowseData>(jBrowseData,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

}

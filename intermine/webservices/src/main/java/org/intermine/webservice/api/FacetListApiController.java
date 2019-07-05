package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.FacetList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.intermine.webservice.server.FacetListService;
import org.intermine.webservice.server.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@Controller
public class FacetListApiController extends InterMineController implements FacetListApi {

    private static final Logger LOG = Logger.getLogger(FacetListApiController.class);

    @Autowired
    public FacetListApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<FacetList> facetlist() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        FacetListService facetListService = new FacetListService(im, format);
        try {
            facetListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        FacetList facetList = facetListService.getFacetList();
        setFooter(facetList);

        return new ResponseEntity<FacetList>(facetList,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

}

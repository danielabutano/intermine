package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.FacetSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.FacetService;
import org.intermine.webservice.server.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
@Controller
public class FacetsApiController extends InterMineController implements FacetsApi {

    private static final Logger log = LoggerFactory.getLogger(FacetsApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public FacetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<FacetSearch> facetSearchGet(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        initController();
        FacetSearch facetSearch = serve();
        setFooter(facetSearch);
        return new ResponseEntity<FacetSearch>(facetSearch,httpHeaders,httpStatus);
    }

    public ResponseEntity<FacetSearch> facetSearchPost(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        initController();
        FacetSearch facetSearch = serve();
        setFooter(facetSearch);
        return new ResponseEntity<FacetSearch>(facetSearch,httpHeaders,httpStatus);
    }

    private FacetSearch serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        FacetService facetService = new FacetService(im, format);
        try {
            facetService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        return facetService.getFacetSearch();
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

}

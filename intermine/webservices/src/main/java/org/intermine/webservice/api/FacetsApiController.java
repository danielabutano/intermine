package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.FacetSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.FacetService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
@Controller
public class FacetsApiController extends InterMineController implements FacetsApi {

    private static final Logger log = LoggerFactory.getLogger(FacetsApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public FacetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<FacetSearch> facetSearchGet(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        FacetSearch facetSearch = serve();
        setFooter(facetSearch);
        return new ResponseEntity<FacetSearch>(facetSearch,httpHeaders,getHttpStatus());
    }

    public ResponseEntity<FacetSearch> facetSearchPost(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        FacetSearch facetSearch = serve();
        setFooter(facetSearch);
        return new ResponseEntity<FacetSearch>(facetSearch,httpHeaders,getHttpStatus());
    }

    private FacetSearch serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        FacetService facetService = new FacetService(im);
        try {
            facetService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        httpHeaders = facetService.getResponseHeaders();
        return facetService.getFacetSearch();
    }

}

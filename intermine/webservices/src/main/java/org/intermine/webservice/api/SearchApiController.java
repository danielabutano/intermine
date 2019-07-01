package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.QuickSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.search.QuickSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class SearchApiController extends InterMineController implements SearchApi {

    private static final Logger log = LoggerFactory.getLogger(SearchApiController.class);

    @Autowired
    private ServletContext servletContext;

    @org.springframework.beans.factory.annotation.Autowired
    public SearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<QuickSearch> quickSearchGet(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "The maximum number of records to return. If no limit is provided, up to 100 results will be returned.") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false) Integer start,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        QuickSearch quickSearch = serve();
        setFooter(quickSearch);
        return new ResponseEntity<QuickSearch>(quickSearch,httpHeaders,httpStatus);
    }

    public ResponseEntity<QuickSearch> quickSearchPost(@ApiParam(value = "The query string to search with. If absent, or blank, all results will be returned.") @Valid @RequestParam(value = "q", required = false) String q,@ApiParam(value = "The maximum number of records to return. If no limit is provided, up to 100 results will be returned.") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false) Integer start,@ApiParam(value = "A list to search within.") @Valid @RequestParam(value = "list", required = false) String list,@ApiParam(value = "A facet parameter, eg facet_Organism=D.melanogaster") @Valid @RequestParam(value = "facet_?", required = false) List<String> facet) {
        QuickSearch quickSearch = serve();
        setFooter(quickSearch);
        return new ResponseEntity<QuickSearch>(quickSearch,httpHeaders,httpStatus);
    }

    private QuickSearch serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QuickSearchService quickSearchService = new QuickSearchService(im,servletContext, format);
        try {
            quickSearchService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        return quickSearchService.getQuickSearch();
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

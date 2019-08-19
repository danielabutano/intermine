package org.intermine.bio.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.bio.webservice.BEDQueryService;
import org.intermine.bio.webservice.FastaQueryService;
import org.intermine.bio.webservice.GFFQueryService;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.api.InterMineController;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@Controller
public class BioQueryApiController extends InterMineController implements BioQueryApi {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(BioQueryApiController.class);

    /**
     * so class names
     */
    public static final String SO_CLASS_NAMES = "SO_CLASS_NAMES";
    private static final String RESOURCE = "/WEB-INF/soClassName.properties";

    @Autowired
    ServletContext sc;

    @org.springframework.beans.factory.annotation.Autowired
    public BioQueryApiController(ObjectMapper objectMapper, HttpServletRequest request) throws ServletException {
        super(objectMapper, request);
    }

    @Override
    public void initController(){
        super.initController();
        if (InterMineContext.getAttribute(SO_CLASS_NAMES) == null) {
            Properties soNameProperties = new Properties();
            try {
                InputStream is = sc.getResourceAsStream(RESOURCE);
                if (is == null) {
                    throw new ServletException("Could not find " + RESOURCE);
                }
                soNameProperties.load(is);
            } catch (Exception e) {
                try {
                    throw new ServletException("Error loading so class name mapping file", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
            InterMineContext.setAttribute(SO_CLASS_NAMES, soNameProperties);
        }
    }

    public ResponseEntity<String> queryResultsBedGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        return serveQueryResultsBed(query, null);
    }

    public ResponseEntity<String> queryResultsBedPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        return serveQueryResultsBed(query, null);
    }

    private ResponseEntity<String> serveQueryResultsBed(String query, List<String> views){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        BEDQueryService bedQueryService = new BEDQueryService(im, getFormat(), query, views);
        try {
            bedQueryService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = bedQueryService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.bed", "text/x-ucsc-bed");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

    public ResponseEntity<String> queryResultsFastaGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        initController();
        return serveQueryResultsFasta(query, view);
    }

    public ResponseEntity<String> queryResultsFastaPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        initController();
        return serveQueryResultsFasta(query, view);
    }

    private ResponseEntity<String> serveQueryResultsFasta(String query, List<String> views){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        FastaQueryService fastaQueryService = new FastaQueryService(im, getFormat(), query, views);
        try {
            fastaQueryService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = fastaQueryService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.fa", "text/x-fasta");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }


    public ResponseEntity<String> queryResultsGff3Get(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        initController();
        return serveQueryResultsGff3(query, view);
    }

    public ResponseEntity<String> queryResultsGff3Post(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        initController();
        return serveQueryResultsGff3(query, view);
    }

    private ResponseEntity<String> serveQueryResultsGff3(String query, List<String> views){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GFFQueryService gffQueryService = new GFFQueryService(im, getFormat(), query, views);
        try {
            gffQueryService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = gffQueryService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.gff3", "text/x-gff3");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

    @Override
    public Format getDefaultFormat() {
        return Format.DEFAULT;
    }

}

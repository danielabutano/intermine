package org.intermine.bio.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.api.InterMineAPI;
import org.intermine.bio.webservice.FastaListService;
import org.intermine.bio.webservice.GFF3ListService;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.api.InterMineController;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Properties;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@Controller
public class BioListApiController extends InterMineController implements BioListApi {

    private static final Logger log = LoggerFactory.getLogger(BioListApiController.class);

    /**
     * so class names
     */
    public static final String SO_CLASS_NAMES = "SO_CLASS_NAMES";
    private static final String RESOURCE = "/WEB-INF/soClassName.properties";

    @Autowired
    ServletContext sc;

    @org.springframework.beans.factory.annotation.Autowired
    public BioListApiController(ObjectMapper objectMapper, HttpServletRequest request) {
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

    public ResponseEntity<String> listResultsFastaGet(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list) {
        initController();
        return serveListResultsFasta(list);
    }

    public ResponseEntity<String> listResultsFastaPost(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list) {
        initController();
        return serveListResultsFasta(list);
    }

    private ResponseEntity<String> serveListResultsFasta(String list){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        FastaListService fastaListService = new FastaListService(im, getFormat(), list);
        try {
            fastaListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = fastaListService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.bed", "text/x-ucsc-bed");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }


    public ResponseEntity<String> listResultsGff3Get(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list) {
        initController();
        return serveListResultsGff3(list);
    }

    public ResponseEntity<String> listResultsGff3Post(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list) {
        initController();
        return serveListResultsGff3(list);
    }

    private ResponseEntity<String> serveListResultsGff3(String list){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GFF3ListService gff3ListService = new GFF3ListService(im, getFormat(), list);
        try {
            gff3ListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = gff3ListService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.bed", "text/x-ucsc-bed");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

}

package org.intermine.bio.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.bio.webservice.GenomicRegionBedService;
import org.intermine.bio.webservice.GenomicRegionFastaService;
import org.intermine.bio.webservice.GenomicRegionGFF3Service;
import org.intermine.bio.webservice.GenomicRegionSearchService;
import org.intermine.bio.webservice.model.ListGenomicIntervals;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.api.InterMineController;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@Controller
public class RegionsApiController extends InterMineController implements RegionsApi {

    private static final Logger log = LoggerFactory.getLogger(RegionsApiController.class);

    /**
     * so class names
     */
    public static final String SO_CLASS_NAMES = "SO_CLASS_NAMES";
    private static final String RESOURCE = "/WEB-INF/soClassName.properties";

    @Autowired
    ServletContext sc;

    @org.springframework.beans.factory.annotation.Autowired
    public RegionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
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

    public ResponseEntity<String> regionsBedGet(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        return serveRegionsBed();
    }

    public ResponseEntity<String> regionsBedPost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        initController();
        return serveRegionsBed();
    }

    private ResponseEntity<String> serveRegionsBed(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GenomicRegionBedService genomicRegionBedService = new GenomicRegionBedService(im, getFormat());
        try {
            genomicRegionBedService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = genomicRegionBedService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.bed", "text/x-ucsc-bed");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

    public ResponseEntity<String> regionsGff3Get(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        return serveRegionsGff3();
    }

    public ResponseEntity<String> regionsGff3Post(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        initController();
        return serveRegionsGff3();
    }

    private ResponseEntity<String> serveRegionsGff3(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GenomicRegionGFF3Service genomicRegionGFF3Service = new GenomicRegionGFF3Service(im, getFormat());
        try {
            genomicRegionGFF3Service.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = genomicRegionGFF3Service.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.gff3", "text/x-gff3");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListGenomicIntervals> regionsListGet(@NotNull @ApiParam(value = "The name of the list to save these features to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "An optional description for this list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to this list (separated by semi-colon).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false) Boolean replaceExisting) {
        initController();
        return serveRegionsList();
    }

    public ResponseEntity<ListGenomicIntervals> regionsListPost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body,@NotNull @ApiParam(value = "The name of the list to save these features to.", required = true) @Valid @RequestParam(value = "listName", required = true) String listName,@ApiParam(value = "An optional description for this list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to this list (separated by semi-colon).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false) Boolean replaceExisting) {
        initController();
        return serveRegionsList();
    }

    private ResponseEntity<ListGenomicIntervals> serveRegionsList(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GenomicRegionSearchService genomicRegionSearchService = new GenomicRegionSearchService(im, getFormat());
        try {
            genomicRegionSearchService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListGenomicIntervals listGenomicIntervals = genomicRegionSearchService.getListGenomicIntervals();
        if (isUncompressed()) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "results.json");
        }
        setFooter(listGenomicIntervals);
        return new ResponseEntity<ListGenomicIntervals>(listGenomicIntervals,httpHeaders,httpStatus);
    }

    public ResponseEntity<String> regionsSequenceGet(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        return serveRegionsFasta();
    }

    public ResponseEntity<String> regionsSequencePost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        initController();
        return serveRegionsFasta();
    }

    private ResponseEntity<String> serveRegionsFasta(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        GenomicRegionFastaService genomicRegionFastaService = new GenomicRegionFastaService(im, getFormat());
        try {
            genomicRegionFastaService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String result = genomicRegionFastaService.getOutputString();
        if (isUncompressed()) {
            ResponseUtilSpring.setCustomTypeHeader(httpHeaders, "results.fa", "text/x-fasta");
        }
        return new ResponseEntity<String>(result,httpHeaders,httpStatus);
    }

    @Override
    public Format getDefaultFormat() {
        return Format.DEFAULT;
    }

}

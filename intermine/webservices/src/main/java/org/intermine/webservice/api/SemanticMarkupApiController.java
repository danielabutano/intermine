package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SemanticMarkup;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.fair.BioEntityMarkupService;
import org.intermine.webservice.server.fair.DataCatalogMarkupService;
import org.intermine.webservice.server.fair.DataSetMarkupService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T01:19:48.599+05:30[Asia/Kolkata]")
@Controller
public class SemanticMarkupApiController extends InterMineController implements SemanticMarkupApi {

    private static final Logger log = LoggerFactory.getLogger(SemanticMarkupApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public SemanticMarkupApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<SemanticMarkup> semanticMarkupDatacatalog() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        DataCatalogMarkupService dataCatalogMarkupService = new DataCatalogMarkupService(im);
        try {
            dataCatalogMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = dataCatalogMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);
        httpHeaders = dataCatalogMarkupService.getResponseHeaders();

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,getHttpStatus());
    }


    public ResponseEntity<SemanticMarkup> semanticMarkupBioEntity(@NotNull @ApiParam(value = "The type of the bioentity.", required = true) @Valid @RequestParam(value = "type", required = true) String type,@NotNull @ApiParam(value = "The primary identifier of the bioentity.", required = true) @Valid @RequestParam(value = "id", required = true) Integer id) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        BioEntityMarkupService bioEntityMarkupService = new BioEntityMarkupService(im);
        try {
            bioEntityMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = bioEntityMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);
        httpHeaders = bioEntityMarkupService.getResponseHeaders();

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,getHttpStatus());
    }

    public ResponseEntity<SemanticMarkup> semanticMarkupDataset(@NotNull @ApiParam(value = "The name of the dataset.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "The description of the dataset.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "The url of the dataset.") @Valid @RequestParam(value = "url", required = false) String url) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        DataSetMarkupService dataSetMarkupService = new DataSetMarkupService(im);
        try {
            dataSetMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = dataSetMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);
        httpHeaders = dataSetMarkupService.getResponseHeaders();

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,getHttpStatus());
    }


}

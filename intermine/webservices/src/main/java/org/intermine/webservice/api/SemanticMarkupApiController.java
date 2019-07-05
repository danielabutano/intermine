package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SemanticMarkup;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.fair.BioEntityMarkupService;
import org.intermine.webservice.server.fair.DataCatalogMarkupService;
import org.intermine.webservice.server.fair.DataSetMarkupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T01:19:48.599+05:30[Asia/Kolkata]")
@Controller
public class SemanticMarkupApiController extends InterMineController implements SemanticMarkupApi {

    private static final Logger log = LoggerFactory.getLogger(SemanticMarkupApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public SemanticMarkupApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<SemanticMarkup> semanticMarkupDatacatalog() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DataCatalogMarkupService dataCatalogMarkupService = new DataCatalogMarkupService(im, format);
        try {
            dataCatalogMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = dataCatalogMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,httpStatus);
    }


    public ResponseEntity<SemanticMarkup> semanticMarkupBioEntity(@NotNull @ApiParam(value = "The type of the bioentity.", required = true) @Valid @RequestParam(value = "type", required = true) String type,@NotNull @ApiParam(value = "The primary identifier of the bioentity.", required = true) @Valid @RequestParam(value = "id", required = true) Integer id) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        BioEntityMarkupService bioEntityMarkupService = new BioEntityMarkupService(im, format);
        try {
            bioEntityMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = bioEntityMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,httpStatus);
    }

    public ResponseEntity<SemanticMarkup> semanticMarkupDataset(@NotNull @ApiParam(value = "The name of the dataset.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "The description of the dataset.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "The url of the dataset.") @Valid @RequestParam(value = "url", required = false) String url) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DataSetMarkupService dataSetMarkupService = new DataSetMarkupService(im, format);
        try {
            dataSetMarkupService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SemanticMarkup semanticMarkup = dataSetMarkupService.getSemanticMarkup();
        setFooter(semanticMarkup);

        return new ResponseEntity<SemanticMarkup>(semanticMarkup,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }


}

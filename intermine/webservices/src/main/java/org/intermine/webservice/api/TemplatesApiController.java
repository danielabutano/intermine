package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SimpleJsonModel;
import org.intermine.webservice.model.Templates;
import org.intermine.webservice.model.TemplatesSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.template.AvailableTemplatesService;
import org.intermine.webservice.server.template.SystemTemplatesService;
import org.intermine.webservice.server.template.TemplateUploadService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T01:19:48.599+05:30[Asia/Kolkata]")
@Controller
public class TemplatesApiController extends InterMineController implements TemplatesApi {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(TemplatesApiController.class);

    private static final String FILE_BASE_NAME = "templates";


    @org.springframework.beans.factory.annotation.Autowired
    public TemplatesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<?> templatesSystem(@ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SystemTemplatesService systemTemplatesService = new SystemTemplatesService(im, getFormat());
        try {
            systemTemplatesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        TemplatesSystem templatesSystem = systemTemplatesService.getTemplatesSystem();
        if(format.equals("json")) {
            setFooter(templatesSystem);
            return new ResponseEntity<TemplatesSystem>(templatesSystem, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        return new ResponseEntity<Object>(templatesSystem.getTemplates(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> templatesGet(@ApiParam(value = "Whether or not to include invalid templates. Invalid templates cannot be run.") @Valid @RequestParam(value = "includeBroken", required = false) Boolean includeBroken, @ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        AvailableTemplatesService availableTemplatesService = new AvailableTemplatesService(im, getFormat());
        try {
            availableTemplatesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Templates templates = availableTemplatesService.getTemplatesResponse();
        if(format.equals("json")) {
            setFooter(templates);
            return new ResponseEntity<Templates>(templates, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        return new ResponseEntity<Object>(templates.getTemplates(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> templatesPost(@ApiParam(value = "The templates to upload. If using body content."  )  @Valid @RequestBody String body, @ApiParam(value = "The xml or JSON to load, if using form parameters.") @Valid @RequestParam(value = "xml", required = false) String xml, @ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateUploadService templateUploadService = new TemplateUploadService(im, getFormat(), body);
        try {
            templateUploadService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SimpleJsonModel simpleJsonModel = templateUploadService.getSimpleJsonModel();
        if(format.equals("json")) {
            setFooter(simpleJsonModel);
            return new ResponseEntity<SimpleJsonModel>(simpleJsonModel, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> templateUploadGet(@NotNull @ApiParam(value = "One or more templates, serialised in XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query, @ApiParam(value = "The version of the XML format.") @Valid @RequestParam(value = "version", required = false) Integer version, @ApiParam(value = "", allowableValues = "text, json, html, xml") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        SimpleJsonModel simpleJsonModel = serveTemplateUpload();
        if(format.equals("json")) {
            setFooter(simpleJsonModel);
            return new ResponseEntity<SimpleJsonModel>(simpleJsonModel, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> templateUploadPost(@NotNull @ApiParam(value = "One or more templates, serialised in XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The version of the XML format.") @Valid @RequestParam(value = "version", required = false) Integer version,@ApiParam(value = "", allowableValues = "text, json, html, xml") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        SimpleJsonModel simpleJsonModel = serveTemplateUpload();
        if(format.equals("json")) {
            setFooter(simpleJsonModel);
            return new ResponseEntity<SimpleJsonModel>(simpleJsonModel, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    private SimpleJsonModel serveTemplateUpload(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateUploadService templateUploadService = new TemplateUploadService(im, getFormat());
        try {
            templateUploadService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        return templateUploadService.getSimpleJsonModel();
    }



    @Override
    protected Format getDefaultFormat() {
        return Format.XML;
    }

    @Override
    protected boolean canServe(Format format) {
        return Format.BASIC_FORMATS.contains(format);
    }


}

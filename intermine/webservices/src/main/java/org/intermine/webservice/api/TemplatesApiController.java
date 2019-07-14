package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.SavedTemplate;
import org.intermine.webservice.model.SimpleJsonModel;
import org.intermine.webservice.model.TemplateTags;
import org.intermine.webservice.model.Templates;
import org.intermine.webservice.model.TemplatesSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.WebServiceRequestParser;
import org.intermine.webservice.server.template.AvailableTemplatesService;
import org.intermine.webservice.server.template.SingleTemplateService;
import org.intermine.webservice.server.template.SystemTemplatesService;
import org.intermine.webservice.server.template.TemplateRemovalService;
import org.intermine.webservice.server.template.TemplateTagAddingService;
import org.intermine.webservice.server.template.TemplateTagRemovalService;
import org.intermine.webservice.server.template.TemplateTagService;
import org.intermine.webservice.server.template.TemplateUploadService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    public ResponseEntity<?> savedTemplateDelete(@ApiParam(value = "The name of the template to delete.",required=true) @PathVariable("name") String name, @ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateRemovalService templateRemovalService = new TemplateRemovalService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            templateRemovalService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        if(format.equals("json")) {
            SimpleJsonModel simpleJsonModel = new SimpleJsonModel();
            ResponseUtilSpring.setJSONHeader(httpHeaders, FILE_BASE_NAME + ".json", false);
            setFooter(simpleJsonModel);
            return new ResponseEntity<SimpleJsonModel>(simpleJsonModel, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> savedTemplateGet(@ApiParam(value = "The name of the template to fetch.",required=true) @PathVariable("name") String name, @ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SingleTemplateService singleTemplateService = new SingleTemplateService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            singleTemplateService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SavedTemplate savedTemplate = singleTemplateService.getSavedTemplate();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, FILE_BASE_NAME + ".json", false);
            setFooter(savedTemplate);
            return new ResponseEntity<SavedTemplate>(savedTemplate, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, name + ".xml");
        return new ResponseEntity<Object>(savedTemplate.getTemplate(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> templateTagsDelete(@NotNull @ApiParam(value = "The name of a template to add the tag(s) to.", required = true) @Valid @RequestParam(value = "name", required = true) String name, @NotNull @ApiParam(value = "The name of the tags to remove. It should take to from of a semi-colon delimited concatenation of the tag names.", required = true) @Valid @RequestParam(value = "tags", required = true) String tags, @ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateTagRemovalService templateTagRemovalService = new TemplateTagRemovalService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            templateTagRemovalService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        TemplateTags templateTags = templateTagRemovalService.getTemplateTags();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(templateTags);
            return new ResponseEntity<TemplateTags>(templateTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(templateTags.getTags(),httpHeaders,httpStatus);

    }

    public ResponseEntity<?> templateTagsGet(@ApiParam(value = "The name of a template whose tags to retrieve. If no template is provided, then all the tags associated with the authenticating user will be returned.") @Valid @RequestParam(value = "name", required = false) String name,@ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateTagService templateTagService = new TemplateTagService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            templateTagService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        TemplateTags templateTags = templateTagService.getTemplateTags();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(templateTags);
            return new ResponseEntity<TemplateTags>(templateTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(templateTags.getTags(),httpHeaders,httpStatus);

    }

    public ResponseEntity<?> templateTagsPost(@NotNull @ApiParam(value = "The name of a template to add the tag(s) to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of the tags to add. It should take to from of a semi-colon delimited concatenation of the tag names.", required = true) @Valid @RequestParam(value = "tags", required = true) String tags,@ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TemplateTagAddingService templateTagAddingService = new TemplateTagAddingService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            templateTagAddingService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        TemplateTags templateTags = templateTagAddingService.getTemplateTags();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(templateTags);
            return new ResponseEntity<TemplateTags>(templateTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(templateTags.getTags(),httpHeaders,httpStatus);

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

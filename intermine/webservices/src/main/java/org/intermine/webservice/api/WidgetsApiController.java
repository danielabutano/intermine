package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Widgets;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.widget.AvailableWidgetsService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-20T18:29:46.239+05:30[Asia/Kolkata]")
@Controller
public class WidgetsApiController extends InterMineController implements WidgetsApi {

    private static final Logger log = LoggerFactory.getLogger(WidgetsApiController.class);

    private static final String FILE_BASE_NAME = "widgets";

    @org.springframework.beans.factory.annotation.Autowired
    public WidgetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<?> widgetsGet(@ApiParam(value = "", allowableValues = "json, xml, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        AvailableWidgetsService availableWidgetsService = new AvailableWidgetsService(im, getFormat());
        try {
            availableWidgetsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Widgets widgets = availableWidgetsService.getWidgetsModel();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, FILE_BASE_NAME + ".json", false);
            setFooter(widgets);
            return new ResponseEntity<Widgets>(widgets, httpHeaders,httpStatus);
        } else if(format.equals("xml")) {
            ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        } else if(format.equals("tab")) {
            ResponseUtilSpring.setTabHeader(httpHeaders, FILE_BASE_NAME + ".tsv");
        } else if(format.equals("csv")) {
            ResponseUtilSpring.setCSVHeader(httpHeaders, FILE_BASE_NAME + ".csv");
        }
        return new ResponseEntity<Object>(widgets.getWidgets(),httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON
                || format == Format.XML
                || Format.FLAT_FILES.contains(format);
    }
}

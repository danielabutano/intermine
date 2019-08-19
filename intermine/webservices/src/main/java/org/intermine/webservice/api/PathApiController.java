package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.PossibleValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.path.PossibleValuesService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@Controller
public class PathApiController extends InterMineController implements PathApi {

    private static final Logger log = LoggerFactory.getLogger(PathApiController.class);


    @org.springframework.beans.factory.annotation.Autowired
    public PathApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }


    public ResponseEntity<?> pathValuesGet(@ApiParam(value = "The path whose possible values are requested.",required=true)@Valid @RequestParam("path") String path,@ApiParam(value = "A json object mapping which describes the type constraints on this path.") @Valid @RequestParam(value = "typeConstraints", required = false) String typeConstraints,@ApiParam(value = "", allowableValues = "json, jsoncount, count") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return servePathValues(path, typeConstraints, format);
    }

    public ResponseEntity<?> pathValuesPost(@ApiParam(value = "The path whose possible values are requested.",required=true)@Valid @RequestParam("path") String path,@ApiParam(value = "A json object mapping which describes the type constraints on this path.") @Valid @RequestParam(value = "typeConstraints", required = false) String typeConstraints,@ApiParam(value = "", allowableValues = "json, jsoncount, count") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return servePathValues(path, typeConstraints, format);
    }

    private ResponseEntity<?> servePathValues(String path, String typeConstraints, String format){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        PossibleValuesService possibleValuesService = new PossibleValuesService(im, getFormat(), path, typeConstraints);
        try {
            possibleValuesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        PossibleValues possibleValues = possibleValuesService.getPossibleValues();
        if(format.equals("json")||format.equals("jsoncount")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "possibleValues" + ".json", false);
            setFooter(possibleValues);
            return new ResponseEntity<PossibleValues>(possibleValues, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setPlainTextHeader(httpHeaders, "count" + ".text");
        return new ResponseEntity<Integer>(possibleValues.getCount(),httpHeaders,httpStatus);
    }


    @Override
    protected Format getDefaultFormat() {
        return Format.OBJECTS;
    }

    @Override
    protected boolean canServe(Format format) {
        switch (format) {
            case OBJECTS:
            case JSON:
            case TEXT:
                return true;
            default:
                return false;
        }
    }


}

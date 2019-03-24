package org.intermine.webservicesspring.api;

import org.intermine.web.logic.Constants;
import org.intermine.webservicesspring.model.Version;
import org.intermine.webservicesspring.model.VersionRelease;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-23T11:29:25.537+05:30[Asia/Kolkata]")
@Controller
public class VersionApiController implements VersionApi {

    private static final Logger log = LoggerFactory.getLogger(VersionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public VersionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> version(@ApiParam(value = "") @Valid @RequestParam(value = "format", required = false) String format) {
        String accept = request.getHeader("Accept");

        Integer versionInt = new Integer(Constants.WEB_SERVICE_VERSION);

        if(format == null || format.equals("text")){
            return new ResponseEntity<Integer>(versionInt,HttpStatus.OK);
        }

        Version version = new Version();
        version.setVersion(versionInt);

        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);

        version.setExecutionTime(executionTime);

        return new ResponseEntity<Version>(version,HttpStatus.OK);
    }

    public ResponseEntity<VersionRelease> versionIntermine(@ApiParam(value = "") @Valid @RequestParam(value = "format", required = false) String format) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<VersionRelease>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<VersionRelease> versionRelease(@ApiParam(value = "") @Valid @RequestParam(value = "format", required = false) String format) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<VersionRelease>(HttpStatus.NOT_IMPLEMENTED);
    }

}

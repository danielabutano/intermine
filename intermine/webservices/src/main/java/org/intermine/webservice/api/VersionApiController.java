package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Version;
import org.intermine.webservice.model.VersionRelease;
import org.intermine.webservice.server.VersionIntermineService;
import org.intermine.webservice.server.VersionReleaseService;
import org.intermine.webservice.server.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class VersionApiController implements VersionApi {

    private static final Logger LOG = Logger.getLogger(VersionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;

    @Autowired
    public VersionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> version(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        VersionService versionService = new VersionService(interMineAPI);
        try {
            versionService.service(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Version version = versionService.getVersion();
        httpHeaders = versionService.getResponseHeaders();
        httpStatus = versionService.getHttpStatus();
        if(format.equals("json")) {
            versionService.setFooter();
            return new ResponseEntity<Version>(version, httpHeaders, httpStatus);
        }
        return new ResponseEntity<Integer>(version.getVersion(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> versionIntermine(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        VersionIntermineService versionIntermineService = new VersionIntermineService(interMineAPI);
        try {
            versionIntermineService.service(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        VersionRelease versionIntermine = versionIntermineService.getVersionRelease();
        httpHeaders = versionIntermineService.getResponseHeaders();
        httpStatus = versionIntermineService.getHttpStatus();
        if(format.equals("json")) {
            versionIntermineService.setFooter();
            return new ResponseEntity<VersionRelease>(versionIntermine, httpHeaders, httpStatus);
        }
        return new ResponseEntity<String>(versionIntermine.getVersion(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> versionRelease(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        VersionReleaseService versionReleaseService = new VersionReleaseService(interMineAPI);
        try {
            versionReleaseService.service(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        VersionRelease versionRelease = versionReleaseService.getVersionRelease();
        httpHeaders = versionReleaseService.getResponseHeaders();
        httpStatus = versionReleaseService.getHttpStatus();
        if(format.equals("json")) {
            versionReleaseService.setFooter();
            return new ResponseEntity<VersionRelease>(versionRelease, httpHeaders, httpStatus);
        }
        return new ResponseEntity<String>(versionRelease.getVersion(),httpHeaders,httpStatus);
    }

}

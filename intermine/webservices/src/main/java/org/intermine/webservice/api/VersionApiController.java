package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Version;
import org.intermine.webservice.model.VersionRelease;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.VersionIntermineService;
import org.intermine.webservice.server.VersionReleaseService;
import org.intermine.webservice.server.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class VersionApiController extends InterMineController implements VersionApi {

    private static final Logger LOG = Logger.getLogger(VersionApiController.class);

    @Autowired
    public VersionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<?> version(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        setHeaders();
        VersionService versionService = new VersionService(interMineAPI, getFormat());
        try {
            versionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Version version = versionService.getVersion();
        if(format.equals("json")) {
            setFooter(version);
            return new ResponseEntity<Version>(version, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Integer>(version.getVersion(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> versionIntermine(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        setHeaders();
        VersionIntermineService versionIntermineService = new VersionIntermineService(interMineAPI, getFormat());
        try {
            versionIntermineService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        VersionRelease versionIntermine = versionIntermineService.getVersionRelease();
        if(format.equals("json")) {
            setFooter(versionIntermine);
            return new ResponseEntity<VersionRelease>(versionIntermine, httpHeaders,httpStatus);
        }
        return new ResponseEntity<String>(versionIntermine.getVersion(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> versionRelease(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format) {
        initController();
        final InterMineAPI interMineAPI = InterMineContext.getInterMineAPI();

        setHeaders();
        VersionReleaseService versionReleaseService = new VersionReleaseService(interMineAPI, getFormat());
        try {
            versionReleaseService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        VersionRelease versionRelease = versionReleaseService.getVersionRelease();
        if(format.equals("json")) {
            setFooter(versionRelease);
            return new ResponseEntity<VersionRelease>(versionRelease, httpHeaders,httpStatus);
        }
        return new ResponseEntity<String>(versionRelease.getVersion(),httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.TEXT;
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON
                || format == Format.HTML
                || format == Format.TEXT;
    }
}

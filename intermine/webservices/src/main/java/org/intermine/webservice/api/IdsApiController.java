package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.IdResolutionPost;
import org.intermine.webservice.model.IdResolutionResults;
import org.intermine.webservice.model.IdResolutionStatus;
import org.intermine.webservice.model.SimpleJsonModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.idresolution.IdResolutionService;
import org.intermine.webservice.server.idresolution.JobJanitor;
import org.intermine.webservice.server.idresolution.JobRemovalService;
import org.intermine.webservice.server.idresolution.JobResultsService;
import org.intermine.webservice.server.idresolution.JobStatusService;
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
public class IdsApiController extends InterMineController implements IdsApi {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(IdsApiController.class);
    private static final long serialVersionUID = -3364780354450369691L;

    private JobJanitor janitor = null;
    private Thread janitorThread = null;

    @org.springframework.beans.factory.annotation.Autowired
    public IdsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
        try {
            janitor = new JobJanitor();
            janitorThread = new Thread(janitor);
            janitorThread.setDaemon(true);
            janitorThread.start();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }


    @Override
    @org.springframework.beans.factory.annotation.Autowired
    public void finalize() throws Throwable {
        if (janitor != null) {
            janitor.stop();
        }
        if (janitorThread != null) {
            janitorThread.interrupt();
        }
        super.finalize();
    }

    public ResponseEntity<SimpleJsonModel> idsDelete(@ApiParam(value = "The unique identifier of the job.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        JobRemovalService jobRemovalService = new JobRemovalService(im, format, uid);
        try {
            jobRemovalService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SimpleJsonModel simpleJsonModel = new SimpleJsonModel();
        setFooter(simpleJsonModel);

        return new ResponseEntity<SimpleJsonModel>(simpleJsonModel,httpHeaders,httpStatus);

    }

    public ResponseEntity<IdResolutionPost> idsPost(@ApiParam(value = "A representation of the job. This must include a list of identifiers to resolve, and the type of object these ids are meant to resolve to."  )  @Valid @RequestBody String body) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        IdResolutionService idResolutionService = new IdResolutionService(im, format, body);
        try {
            idResolutionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        IdResolutionPost idResolutionPost = idResolutionService.getIdResolutionPost();
        setFooter(idResolutionPost);

        return new ResponseEntity<IdResolutionPost>(idResolutionPost,httpHeaders,httpStatus);
    }

    public ResponseEntity<IdResolutionResults> idsResultsGet(@ApiParam(value = "The unique identifier of the job.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        JobResultsService jobResultsService = new JobResultsService(im, format, uid);
        try {
            jobResultsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        IdResolutionResults idResolutionResults = jobResultsService.getIdResolutionResults();
        setFooter(idResolutionResults);

        return new ResponseEntity<IdResolutionResults>(idResolutionResults,httpHeaders,httpStatus);

    }

    public ResponseEntity<IdResolutionStatus> idsStatusGet(@ApiParam(value = "The unique identifier of the job.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        JobStatusService jobStatusService = new JobStatusService(im, format, uid);
        try {
            jobStatusService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        IdResolutionStatus idResolutionStatus = jobStatusService.getIdResolutionStatus();
        setFooter(idResolutionStatus);

        return new ResponseEntity<IdResolutionStatus>(idResolutionStatus,httpHeaders,httpStatus);

    }

}

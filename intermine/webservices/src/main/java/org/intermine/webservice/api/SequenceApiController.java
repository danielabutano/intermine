package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.clob.SequenceService;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class SequenceApiController extends InterMineController implements SequenceApi {

    private static final Logger log = LoggerFactory.getLogger(SequenceApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public SequenceApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Sequence> sequenceGet(@NotNull @ApiParam(value = "The xml OR JSON of the query to run.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The start index.") @Valid @RequestParam(value = "start", required = false) Integer start,@ApiParam(value = "The end index.") @Valid @RequestParam(value = "end", required = false) Integer end) {
        Sequence sequence = serve();
        setFooter(sequence);
        return new ResponseEntity<Sequence>(sequence,httpHeaders,getHttpStatus());
    }

    public ResponseEntity<Sequence> sequencePost(@NotNull @ApiParam(value = "The xml OR JSON of the query to run.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The start index.") @Valid @RequestParam(value = "start", required = false) Integer start,@ApiParam(value = "The end index.") @Valid @RequestParam(value = "end", required = false) Integer end) {
        Sequence sequence = serve();
        setFooter(sequence);
        return new ResponseEntity<Sequence>(sequence,httpHeaders,getHttpStatus());
    }

    private Sequence serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        SequenceService sequenceService = new SequenceService(im);
        try {
            sequenceService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        httpHeaders = sequenceService.getResponseHeaders();

        return sequenceService.getSequence();
    }

}

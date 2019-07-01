package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.clob.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

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
        return new ResponseEntity<Sequence>(sequence,httpHeaders,httpStatus);
    }

    public ResponseEntity<Sequence> sequencePost(@NotNull @ApiParam(value = "The xml OR JSON of the query to run.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The start index.") @Valid @RequestParam(value = "start", required = false) Integer start,@ApiParam(value = "The end index.") @Valid @RequestParam(value = "end", required = false) Integer end) {
        Sequence sequence = serve();
        setFooter(sequence);
        return new ResponseEntity<Sequence>(sequence,httpHeaders,httpStatus);
    }

    private Sequence serve(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SequenceService sequenceService = new SequenceService(im, format);
        try {
            sequenceService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }

        return sequenceService.getSequence();
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

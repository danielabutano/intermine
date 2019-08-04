package org.intermine.bio.webservice.api;

import org.intermine.bio.webservice.model.ListGenomicIntervals;
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
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@Controller
public class RegionsApiController implements RegionsApi {

    private static final Logger log = LoggerFactory.getLogger(RegionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RegionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> regionsBedGet(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> regionsBedPost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> regionsGff3Get(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> regionsGff3Post(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListGenomicIntervals> regionsListGet(@NotNull @ApiParam(value = "The name of the list to save these features to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "An optional description for this list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to this list (separated by semi-colon).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false) Boolean replaceExisting) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ListGenomicIntervals>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListGenomicIntervals> regionsListPost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body,@NotNull @ApiParam(value = "The name of the list to save these features to.", required = true) @Valid @RequestParam(value = "listName", required = true) String listName,@ApiParam(value = "An optional description for this list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to this list (separated by semi-colon).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false) Boolean replaceExisting) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ListGenomicIntervals>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> regionsSequenceGet(@NotNull @ApiParam(value = "The region search input.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> regionsSequencePost(@ApiParam(value = "A representation of the search request." ,required=true )  @Valid @RequestBody String body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

}

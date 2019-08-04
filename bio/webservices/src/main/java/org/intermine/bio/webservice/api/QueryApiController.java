package org.intermine.bio.webservice.api;

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
public class QueryApiController implements QueryApi {

    private static final Logger log = LoggerFactory.getLogger(QueryApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public QueryApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> queryResultsBedGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> queryResultsBedPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> queryResultsFastaGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> queryResultsFastaPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> queryResultsGff3Get(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> queryResultsGff3Post(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Extra columns to include as extra information in the ninth column.") @Valid @RequestParam(value = "view", required = false) List<String> view) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

}

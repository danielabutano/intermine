package org.intermine.webservice.api;

import io.swagger.annotations.*;
import org.intermine.webservice.model.QueryResultsJson;
import org.intermine.webservice.model.QueryStore;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Api(value = "query", description = "the query API")
public interface QueryApiDefaultJson {

    @ApiOperation(value = "Get a stored query.", nickname = "queriesGet", notes = "Get a query stored at the service.", response = Object.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Object.class) })
    @RequestMapping(value = "/queries",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Object> queriesGet(@NotNull @ApiParam(value = "The id of the query to fetch.", required = true) @Valid @RequestParam(value = "id", required = true) String id,@ApiParam(value = "", allowableValues = "json, xml") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);


    @ApiOperation(value = "Store a query.", nickname = "queriesPost", notes = "", response = QueryStore.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueryStore.class) })
    @RequestMapping(value = "/queries",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<QueryStore> queriesPost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "query", required = true) String query);

    @ApiOperation(value = "Get results for a query against the database.", nickname = "queryResultsGet", notes = "This service provides full access to arbitrary database queries.     Queries are accepted in a serialised XML format, executed and returned     in a streaming manner. The query format is a custom subset of the capabilities     of SQL; see the InterMine documentation for a full description of the query XML     syntax.", response = QueryResultsJson.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueryResultsJson.class) })
    @RequestMapping(value = "/query/results",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> queryResultsGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The version of the XML format used.") @Valid @RequestParam(value = "version", required = false) Integer version,@ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,@ApiParam(value = "The maximum size of the result set.") @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,@ApiParam(value = "Include column headers. Use friendly for human readable paths. (Only for flat-file formats).", allowableValues = "none, path, friendly") @Valid @RequestParam(value = "columnheaders", required = false, defaultValue = "none") String columnheaders,@ApiParam(value = "", allowableValues = "tab, csv, count, json, jsonobject, jsoncount, xml, html") @Valid @RequestParam(value = "format", required = false, defaultValue = "tab") String format);


    @ApiOperation(value = "Get results for a query against the database.", nickname = "queryResultsPost", notes = "This service provides full access to arbitrary database queries.     Queries are accepted in a serialised XML format, executed and returned     in a streaming manner. The query format is a custom subset of the capabilities     of SQL; see the InterMine documentation for a full description of the query XML     syntax.", response = QueryResultsJson.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = QueryResultsJson.class) })
    @RequestMapping(value = "/query/results",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> queryResultsPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The version of the XML format used.") @Valid @RequestParam(value = "version", required = false) Integer version,@ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,@ApiParam(value = "The maximum size of the result set.") @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,@ApiParam(value = "Include column headers. Use friendly for human readable paths. (Only for flat-file formats).", allowableValues = "none, path, friendly") @Valid @RequestParam(value = "columnheaders", required = false, defaultValue = "none") String columnheaders,@ApiParam(value = "", allowableValues = "tab, csv, count, json, jsonobject, jsoncount, xml, html") @Valid @RequestParam(value = "format", required = false, defaultValue = "tab") String format);

}

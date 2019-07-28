package org.intermine.webservice.api;

import io.swagger.annotations.*;
import org.intermine.webservice.model.QueryResultsJson;
import org.intermine.webservice.model.QueryStore;
import org.intermine.webservice.model.ToList;
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

    @ApiOperation(value = "Add the result set of a query to a list on the server.", nickname = "queryAppendToListGet", notes = "This service provides the facility to submit a query and add the objects           contained in its result set to a list that already exists on the server. This           facility places a couple of restrictions on the query itself, namely that           the view list may only contain a single item, which should refer to the           internal id attribute of an object in the query.", response = ToList.class, authorizations = {
            @Authorization(value = "ApiKeyAuthToken"),
            @Authorization(value = "BasicAuth"),
            @Authorization(value = "JWTBearerAuth")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ToList.class) })
    @RequestMapping(value = "/query/append/tolist",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> queryAppendToListGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The list to append items to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);


    @ApiOperation(value = "Add the result set of a query to a list on the server.", nickname = "queryAppendToListPost", notes = "This service provides the facility to submit a query and add the objects           contained in its result set to a list that already exists on the server. This           facility places a couple of restrictions on the query itself, namely that           the view list may only contain a single item, which should refer to the           internal id attribute of an object in the query.", response = ToList.class, authorizations = {
            @Authorization(value = "ApiKeyAuthToken"),
            @Authorization(value = "BasicAuth"),
            @Authorization(value = "JWTBearerAuth")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ToList.class) })
    @RequestMapping(value = "/query/append/tolist",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> queryAppendToListPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The list to append items to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);


    @ApiOperation(value = "Save the result set of a query as a list on the server.", nickname = "queryToListGet", notes = "This service provides the facility to submit a query and create a new list whose contents shall be the result set defined by running the query. <br/><br/> This facility places a couple of restrictions on the query itself, namely that the view list may only contain a single item, which can refer to any attribute of an object. The attribute itself will be ignored, and the object itself will be selected. For this reason, one might choose to always select the `.id` attribute when using this service.", response = ToList.class, authorizations = {
            @Authorization(value = "ApiKeyAuthToken"),
            @Authorization(value = "BasicAuth"),
            @Authorization(value = "JWTBearerAuth")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ToList.class) })
    @RequestMapping(value = "/query/tolist",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> queryToListGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query, @NotNull @ApiParam(value = "The name for the new list. There must be no existing list of this name.", required = true) @Valid @RequestParam(value = "name", required = true) String name, @ApiParam(value = "A description to attach to the new list.") @Valid @RequestParam(value = "description", required = false) String description, @ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags, @ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false, defaultValue = "false") Boolean replaceExisting, @ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);


    @ApiOperation(value = "Save the result set of a query as a list on the server.", nickname = "queryToListPost", notes = "This service provides the facility to submit a query and create a new list whose contents shall be the result set defined by running the query. <br/><br/> This facility places a couple of restrictions on the query itself, namely that the view list may only contain a single item, which can refer to any attribute of an object. The attribute itself will be ignored, and the object itself will be selected. For this reason, one might choose to always select the `.id` attribute when using this service.", response = ToList.class, authorizations = {
            @Authorization(value = "ApiKeyAuthToken"),
            @Authorization(value = "BasicAuth"),
            @Authorization(value = "JWTBearerAuth")    }, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ToList.class) })
    @RequestMapping(value = "/query/tolist",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> queryToListPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The name for the new list. There must be no existing list of this name.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A description to attach to the new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false, defaultValue = "false") Boolean replaceExisting,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);

}

/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.intermine.webservice.api;

import org.intermine.webservice.model.PossibleValues;
import io.swagger.annotations.*;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@Api(value = "path", description = "the path API")
public interface PathApi {

    @ApiOperation(value = "Get the possible values a path may have.", nickname = "pathValuesGet", notes = "This service provides the possible values that a path may represent        in the database. This functionality is expected to primarily useful for        applications providing completion and suggestions for user-input.", response = PossibleValues.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PossibleValues.class) })
    @RequestMapping(value = "/path/values",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> pathValuesGet(@ApiParam(value = "The path whose possible values are requested.", required = true)@Valid @RequestParam("path") String path, @ApiParam(value = "A json object mapping which describes the type constraints on this path.") @Valid @RequestParam(value = "typeConstraints", required = false) String typeConstraints, @ApiParam(value = "", allowableValues = "json, jsoncount, count") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);


    @ApiOperation(value = "Get the possible values a path may have.", nickname = "pathValuesPost", notes = "This service provides the possible values that a path may represent        in the database. This functionality is expected to primarily useful for        applications providing completion and suggestions for user-input.", response = PossibleValues.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PossibleValues.class) })
    @RequestMapping(value = "/path/values",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<?> pathValuesPost(@ApiParam(value = "The path whose possible values are requested.", required = true)@Valid @RequestParam("path") String path, @ApiParam(value = "A json object mapping which describes the type constraints on this path.") @Valid @RequestParam(value = "typeConstraints", required = false) String typeConstraints, @ApiParam(value = "", allowableValues = "json, jsoncount, count") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format);

}

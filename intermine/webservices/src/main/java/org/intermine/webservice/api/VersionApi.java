/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.intermine.webservice.api;

import io.swagger.annotations.*;
import org.intermine.webservice.model.Version;
import org.intermine.webservice.model.VersionRelease;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Api(value = "version", description = "the version API")
public interface VersionApi {

    @ApiOperation(value = "Get the Web-Service Version.", nickname = "version", notes = "Get the web-service version number. This number is incremented for each change in functionality.", response = Version.class, tags={ "Version" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Version.class) })
    @RequestMapping(value = "/version",
        method = RequestMethod.GET)
    ResponseEntity<?> version(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format);


    @ApiOperation(value = "Get the InterMine Release Version.", nickname = "versionIntermine", notes = "Get the InterMine version number. This number represents the version of InterMine code currently running. See https://github.com/intermine/intermine/releases for full details on release dates and versions.", response = VersionRelease.class, tags={ "Version" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = VersionRelease.class) })
    @RequestMapping(value = "/version/intermine",
        method = RequestMethod.GET)
    ResponseEntity<?> versionIntermine(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format);


    @ApiOperation(value = "Get the Data-Warehouse Release Version.", nickname = "versionRelease", notes = "Get the release version of the data-warehouse. This is a string that is changed each time data is added or removed from the server. Thus this number is meant to reflect the contents rather than the interface of this set of services.", response = VersionRelease.class, tags={ "Version" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = VersionRelease.class) })
    @RequestMapping(value = "/version/release",
        method = RequestMethod.GET)
    ResponseEntity<?> versionRelease(@ApiParam(value = "", allowableValues = "text, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "text") String format);

}

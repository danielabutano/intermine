/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.intermine.webservice.api;

import org.intermine.webservice.model.SummaryFields;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-17T02:38:26.046+05:30[Asia/Kolkata]")
@Api(value = "classkeys", description = "the classkeys API")
public interface ClassKeysApi {

    @ApiOperation(value = "Get the fields used to identify an object.", nickname = "classkeys", notes = "Get the fields configured for this service to identify objects uniquely within the database.", response = SummaryFields.class, tags={ "Key Fields" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = SummaryFields.class) })
    @RequestMapping(value = "/classkeys",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<SummaryFields> classkeys();

}

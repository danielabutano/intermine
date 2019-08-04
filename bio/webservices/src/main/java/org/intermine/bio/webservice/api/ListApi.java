/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.intermine.bio.webservice.api;

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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@Api(value = "list", description = "the list API")
public interface ListApi {

    @ApiOperation(value = "Get the Contents of a List as FASTA..", nickname = "listResultsFastaGet", notes = "This service allows users to export the contents of a list of `SequenceFeature`s or `Protein`s as [FASTA](http://en.wikipedia.org/wiki/FASTA_format)", response = String.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class) })
    @RequestMapping(value = "/list/results/fasta",
        produces = { "text/x-fasta" }, 
        method = RequestMethod.GET)
    ResponseEntity<String> listResultsFastaGet(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list);


    @ApiOperation(value = "Get the Contents of a List as FASTA..", nickname = "listResultsFastaPost", notes = "This service allows users to export the contents of a list of `SequenceFeature`s or `Protein`s as [FASTA](http://en.wikipedia.org/wiki/FASTA_format)", response = String.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class) })
    @RequestMapping(value = "/list/results/fasta",
        produces = { "text/x-fasta" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> listResultsFastaPost(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list);


    @ApiOperation(value = "Get the Contents of a List as GFF3.", nickname = "listResultsGff3Get", notes = "This service allows users to export the contents of a list     of `SequenceFeature`s as GFF3.", response = String.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class) })
    @RequestMapping(value = "/list/results/gff3",
        produces = { "text/x-gff3" }, 
        method = RequestMethod.GET)
    ResponseEntity<String> listResultsGff3Get(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list);


    @ApiOperation(value = "Get the Contents of a List as GFF3.", nickname = "listResultsGff3Post", notes = "This service allows users to export the contents of a list     of `SequenceFeature`s as GFF3.", response = String.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class) })
    @RequestMapping(value = "/list/results/gff3",
        produces = { "text/x-gff3" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> listResultsGff3Post(@NotNull @ApiParam(value = "The name of the list.", required = true) @Valid @RequestParam(value = "list", required = true) String list);

}

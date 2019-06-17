package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.FacetList;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.FacetListService;
import org.springframework.beans.factory.annotation.Autowired;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@Controller
public class FacetListApiController implements FacetListApi {

    private static final Logger LOG = Logger.getLogger(FacetListApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;

    @Autowired
    public FacetListApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<FacetList> facetlist() {
        String accept = request.getHeader("Accept");
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        FacetListService facetListService = new FacetListService(im);
        facetListService.service(request);
        facetListService.setFooter();
        FacetList facetList = facetListService.getFacetList();
        httpHeaders = facetListService.getResponseHeaders();
        httpStatus = facetListService.getHttpStatus();

        return new ResponseEntity<FacetList>(facetList,httpHeaders,httpStatus);
    }

}

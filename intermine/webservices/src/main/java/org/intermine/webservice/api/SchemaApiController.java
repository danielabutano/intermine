package org.intermine.webservice.api;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.metadata.StringUtil;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.SchemaListService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.LoggerFactory;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T00:14:58.996+05:30[Asia/Kolkata]")
@Controller
public class SchemaApiController extends InterMineController implements SchemaApi {

    private static final Logger LOGGER = Logger.getLogger(SchemaApiController.class);

    @Autowired
    ServletContext servletContext;

    @Autowired
    public SchemaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Schema> allSchema() {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        SchemaListService schemaListService = new SchemaListService(im);
        try {
            schemaListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Schema schema = schemaListService.getSchema();
        setFooter(schema);
        httpHeaders = schemaListService.getResponseHeaders();

        return new ResponseEntity<Schema>(schema,httpHeaders,getHttpStatus());
    }

    public ResponseEntity<?> oneSchema(@ApiParam(value = "The name of the schema to retrieve",required=true) @PathVariable("name") String name) {
        httpHeaders = new HttpHeaders();
        String responseObject = serveSpecificSchema(name);
        return new ResponseEntity<String>(responseObject,httpHeaders,getHttpStatus());
    }

    private String serveSpecificSchema(String fileName) {
        Properties webProperties = InterMineContext.getWebProperties();
        Set<String> schemata = new HashSet<String>(
                Arrays.asList(webProperties.getProperty("schema.filenames", "").split(",")));
        if (!schemata.contains(fileName)) {
            httpStatus = HttpStatus.NOT_FOUND;
            return (fileName + " is not in the list of schemata.");
        } else {
            try {
                ResponseUtilSpring.setFileName(httpHeaders, fileName);
                if (fileName.endsWith("schema")) {
                    ResponseUtilSpring.setJSONSchemaContentType(httpHeaders);
                } else if (fileName.endsWith("xsd")) {
                    ResponseUtilSpring.setXMLContentType(httpHeaders);
                }
                InputStream in = servletContext.getResourceAsStream("/webservice/" + fileName);
                return IOUtils.toString(in, "UTF-8");
            } catch (Throwable t) {
                LOGGER.error(t);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                return ("Could not serve " + fileName + ": " + t.getMessage());
            }
        }
    }

}

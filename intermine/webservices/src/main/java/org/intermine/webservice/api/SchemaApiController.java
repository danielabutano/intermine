package org.intermine.webservice.api;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.SchemaListService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
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
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SchemaListService schemaListService = new SchemaListService(im, format);
        try {
            schemaListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Schema schema = schemaListService.getSchema();
        setFooter(schema);

        return new ResponseEntity<Schema>(schema,httpHeaders,httpStatus);
    }

    public ResponseEntity<?> oneSchema(@ApiParam(value = "The name of the schema to retrieve",required=true) @PathVariable("name") String name) {
        initController();
        httpHeaders = new HttpHeaders();
        String responseObject = serveSpecificSchema(name);
        return new ResponseEntity<String>(responseObject,httpHeaders,httpStatus);
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


    @Override
    protected String getDefaultFileName() {
        return "schemata.json";
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

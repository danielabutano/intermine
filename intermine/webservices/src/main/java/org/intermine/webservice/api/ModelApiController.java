package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Model;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.model.ModelService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@Controller
public class ModelApiController extends InterMineController implements ModelApi {

    private static final Logger LOG = Logger.getLogger(ModelApiController.class);

    private static final String FILE_BASE_NAME = "model";


    @Autowired
    public ModelApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<?> model(@ApiParam(value = "", allowableValues = "xml, json") @Valid @RequestParam(value = "format", required = false, defaultValue = "xml") String format) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ModelService modelService = new ModelService(im, getFormat());
        try {
            modelService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Model model = modelService.getResponseModel();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, FILE_BASE_NAME + ".json", false);
            setFooter(model);
            return new ResponseEntity<Model>(model, httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        return new ResponseEntity<Object>(model.getModel(),httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.XML;
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.XML || format == Format.JSON;
    }



}

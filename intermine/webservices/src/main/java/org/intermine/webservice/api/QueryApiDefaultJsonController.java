package org.intermine.webservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.GeneratedCode;
import org.intermine.webservice.model.QueryResultsJson;
import org.intermine.webservice.model.QueryResultsJsonCount;
import org.intermine.webservice.model.QueryResultsJsonObject;
import org.intermine.webservice.model.QueryStore;
import org.intermine.webservice.model.ToList;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.query.CodeService;
import org.intermine.webservice.server.query.QueryListAppendService;
import org.intermine.webservice.server.query.QueryRetrieverService;
import org.intermine.webservice.server.query.QueryStoreService;
import org.intermine.webservice.server.query.QueryToListService;
import org.intermine.webservice.server.query.result.QueryResultService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@Controller
public class QueryApiDefaultJsonController extends InterMineController implements QueryApiDefaultJson {

    private static final Logger log = LoggerFactory.getLogger(QueryApiController.class);

    @org.springframework.beans.factory.annotation.Autowired
    public QueryApiDefaultJsonController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Object> queriesGet(@NotNull @ApiParam(value = "The id of the query to fetch.", required = true) @Valid @RequestParam(value = "id", required = true) String id,@ApiParam(value = "", allowableValues = "json, xml") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QueryRetrieverService queryRetrieverService = new QueryRetrieverService(im, getFormat());
        try {
            queryRetrieverService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        String model = queryRetrieverService.getResult();
        if(format.equals("json")) {
            return new ResponseEntity<Object>(model, httpHeaders,httpStatus);
        } else if(format.equals("xml")) {
            ResponseUtilSpring.setXMLHeader(httpHeaders, "query" + ".xml");
        }
        return new ResponseEntity<Object>(model,httpHeaders,httpStatus);

    }

    public ResponseEntity<QueryStore> queriesPost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "query", required = true) String query) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QueryStoreService queryStoreService = new QueryStoreService(im, getFormat());
        try {
            queryStoreService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        QueryStore queryStore = queryStoreService.getQueryStoreModel();
        setFooter(queryStore);
        return new ResponseEntity<QueryStore>(queryStore,httpHeaders,httpStatus);
    }

    public ResponseEntity<?> queryResultsGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML format.", required = true) @Valid @RequestParam(value = "query", required = true) String query, @ApiParam(value = "The version of the XML format used.") @Valid @RequestParam(value = "version", required = false) Integer version, @ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false, defaultValue = "0") Integer start, @ApiParam(value = "The maximum size of the result set.") @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size, @ApiParam(value = "Include column headers. Use friendly for human readable paths. (Only for flat-file formats).", allowableValues = "none, path, friendly") @Valid @RequestParam(value = "columnheaders", required = false, defaultValue = "none") String columnheaders, @ApiParam(value = "", allowableValues = "tab, csv, count, json, jsonobject, jsoncount, xml, html") @Valid @RequestParam(value = "format", required = false, defaultValue = "tab") String format) {
        initController();
        return serveQueryResults(format);
    }

    public ResponseEntity<?> queryResultsPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "The version of the XML format used.") @Valid @RequestParam(value = "version", required = false) Integer version,@ApiParam(value = "The index of the first result to return.") @Valid @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,@ApiParam(value = "The maximum size of the result set.") @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,@ApiParam(value = "Include column headers. Use friendly for human readable paths. (Only for flat-file formats).", allowableValues = "none, path, friendly") @Valid @RequestParam(value = "columnheaders", required = false, defaultValue = "none") String columnheaders,@ApiParam(value = "", allowableValues = "tab, csv, count, json, jsonobject, jsoncount, xml, html") @Valid @RequestParam(value = "format", required = false, defaultValue = "tab") String format) {
        initController();
        return serveQueryResults(format);

    }

    private ResponseEntity<?> serveQueryResults(String format){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        if(format.equals("tab")){
            this.format = Format.TSV;
        }
        QueryResultService queryResultService = new QueryResultService(im, getFormat());
        try {
            queryResultService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        if(format.equals("json")){

            QueryResultsJson queryResultsJson = queryResultService.getQueryResultsJson();
            ResponseUtilSpring.setJSONHeader(httpHeaders, "results.json", false);
            setFooter(queryResultsJson);
            return new ResponseEntity<QueryResultsJson>(queryResultsJson,httpHeaders,httpStatus);

        } else if(format.equals("jsonobject")) {

            QueryResultsJsonObject queryResultsJsonObject = queryResultService.getQueryResultsJsonObject();
            ResponseUtilSpring.setJSONHeader(httpHeaders, "results.json", false);
            setFooter(queryResultsJsonObject);
            return new ResponseEntity<QueryResultsJsonObject>(queryResultsJsonObject,httpHeaders,httpStatus);

        } else if(format.equals("jsoncount")) {

            QueryResultsJsonCount queryResultsJsonCount = queryResultService.getQueryResultsJsonCount();
            ResponseUtilSpring.setJSONHeader(httpHeaders, "results.json", false);
            setFooter(queryResultsJsonCount);
            return new ResponseEntity<QueryResultsJsonCount>(queryResultsJsonCount,httpHeaders,httpStatus);

        } else if(format.equals("count")) {

            QueryResultsJsonCount queryResultsJsonCount = queryResultService.getQueryResultsJsonCount();
            ResponseUtilSpring.setPlainTextHeader(httpHeaders, "results");
            return new ResponseEntity<Integer>(queryResultsJsonCount.getCount(),httpHeaders,httpStatus);

        } else if(format.equals("tab")) {
            ResponseUtilSpring.setTabHeader(httpHeaders, "results.tsv");

        } else if(format.equals("csv")) {
            ResponseUtilSpring.setCSVHeader(httpHeaders, "results.csv");

        } else if(format.equals("xml")) {
            ResponseUtilSpring.setXMLHeader(httpHeaders, "results.xml");

        }
        QueryResultsJsonObject queryResultsJsonObject = queryResultService.getQueryResultsJsonObject();
        return new ResponseEntity<Object>(queryResultsJsonObject.getResults(),httpHeaders,httpStatus);
    }


    public ResponseEntity<?> queryAppendToListGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query, @NotNull @ApiParam(value = "The list to append items to.", required = true) @Valid @RequestParam(value = "name", required = true) String name, @ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags, @ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveAppendToList(format, name);
    }

    public ResponseEntity<?> queryAppendToListPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The list to append items to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveAppendToList(format, name);
    }

    private ResponseEntity<?> serveAppendToList(String format, String name){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QueryListAppendService queryListAppendService = new QueryListAppendService(im, getFormat(), name);
        try {
            queryListAppendService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }

        ToList toList = queryListAppendService.getToList();

        if(format.equals("json")){
            setFooter(toList);
            return new ResponseEntity<ToList>(toList,httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setPlainTextHeader(httpHeaders,"results.txt");
        return new ResponseEntity<Integer>(toList.getListSize(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> queryToListGet(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The name for the new list. There must be no existing list of this name.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A description to attach to the new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false, defaultValue = "false") Boolean replaceExisting,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveToList(format, name);
    }

    public ResponseEntity<?> queryToListPost(@NotNull @ApiParam(value = "A definition of the query to execute in Path-Query XML or JSON format.", required = true) @Valid @RequestParam(value = "query", required = true) String query,@NotNull @ApiParam(value = "The name for the new list. There must be no existing list of this name.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "A description to attach to the new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to use to categorise the new list separated by semicolon(;).") @Valid @RequestParam(value = "tags", required = false) String tags,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false, defaultValue = "false") Boolean replaceExisting,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveToList(format, name);
    }

    private ResponseEntity<?> serveToList(String format, String name){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        QueryToListService queryToListService = new QueryToListService(im, getFormat(), name);
        try {
            queryToListService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }

        ToList toList = queryToListService.getToList();

        if(format.equals("json")){
            setFooter(toList);
            return new ResponseEntity<ToList>(toList,httpHeaders,httpStatus);
        }
        ResponseUtilSpring.setPlainTextHeader(httpHeaders,"results.txt");
        return new ResponseEntity<Integer>(toList.getListSize(),httpHeaders,httpStatus);
    }

    @Override
    protected String getDefaultFileName() {
        return "query";
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

    private static final Set<Format> MENU = new HashSet<Format>() {
        private static final long serialVersionUID = -6257564064566791521L;
        {
            addAll(Format.BASIC_FORMATS);
            addAll(Format.FLAT_FILES);
            addAll(Format.JSON_FORMATS);
        }
    };

    @Override
    protected boolean canServe(Format format) {
        return MENU.contains(format);
    }

}

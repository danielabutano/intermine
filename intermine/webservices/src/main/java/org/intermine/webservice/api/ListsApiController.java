package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.ListAppend;
import org.intermine.webservice.model.ListRename;
import org.intermine.webservice.model.ListsDelete;
import org.intermine.webservice.model.ListsGet;
import org.intermine.webservice.model.ListsPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.model.Tags;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.WebServiceRequestParser;
import org.intermine.webservice.server.lists.AvailableListsService;
import org.intermine.webservice.server.lists.ListAppendService;
import org.intermine.webservice.server.lists.ListDeletionService;
import org.intermine.webservice.server.lists.ListRenameService;
import org.intermine.webservice.server.lists.ListTagAddingService;
import org.intermine.webservice.server.lists.ListTagRemovalService;
import org.intermine.webservice.server.lists.ListTagService;
import org.intermine.webservice.server.lists.ListUploadService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-16T22:35:41.810+05:30[Asia/Kolkata]")
@Controller
public class ListsApiController extends InterMineController implements ListsApi {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(ListsApiController.class);

    private static final String FILE_BASE_NAME = "";


    @org.springframework.beans.factory.annotation.Autowired
    public ListsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<ListRename> listRenameGet(@NotNull @ApiParam(value = "The current name of the list.", required = true) @Valid @RequestParam(value = "oldname", required = true) String oldname,@NotNull @ApiParam(value = "The name the list should have.", required = true) @Valid @RequestParam(value = "newname", required = true) String newname) {
        initController();
        ListRename listRename = serveListRename();
        setFooter(listRename);
        return new ResponseEntity<ListRename>(listRename,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListRename> listRenamePost(@NotNull @ApiParam(value = "The current name of the list.", required = true) @Valid @RequestParam(value = "oldname", required = true) String oldname,@NotNull @ApiParam(value = "The name the list should have.", required = true) @Valid @RequestParam(value = "newname", required = true) String newname) {
        initController();
        ListRename listRename = serveListRename();
        setFooter(listRename);
        return new ResponseEntity<ListRename>(listRename,httpHeaders,httpStatus);
    }

    private ListRename serveListRename(){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListRenameService listRenameService = new ListRenameService(im, format);
        try {
            listRenameService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        return listRenameService.getListRename();
    }

    public ResponseEntity<?> listsAppendPost(@ApiParam(value = "Identifiers for objects to add to the list, tab, comma or newline separated." ,required=true )  @Valid @RequestBody String body,@NotNull @ApiParam(value = "The name of the list to append to. The list must exist, and belong to you.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListAppendService listAppendService = new ListAppendService(im, getFormat(), body);
        try {
            listAppendService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListAppend listAppend = listAppendService.getListAppend();
        if(format.equals("json")) {
            setFooter(listAppend);
            return new ResponseEntity<ListAppend>(listAppend, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listsDelete(@NotNull @ApiParam(value = "The name of the list to delete.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListDeletionService listDeletionService = new ListDeletionService(im, getFormat());
        try {
            listDeletionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListsDelete listsDelete = listDeletionService.getListsDelete();
        if(format.equals("json")) {
            setFooter(listsDelete);
            return new ResponseEntity<ListsDelete>(listsDelete, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listsGet(@ApiParam(value = "An optional filter by name.") @Valid @RequestParam(value = "name", required = false) String name,@ApiParam(value = "", allowableValues = "json, html, text, csv, tab") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        AvailableListsService availableListsService = new AvailableListsService(im, getFormat());
        try {
            availableListsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListsGet listsGet = availableListsService.getListsGet();
        if(format.equals("json")) {
            setFooter(listsGet);
            return new ResponseEntity<ListsGet>(listsGet, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(listsGet.getLists(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listsPost(@ApiParam(value = "Identifiers for objects to add to the list, tab, comma or newline separated." ,required=true )  @Valid @RequestBody String body,@NotNull @ApiParam(value = "The name of the new list.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The type of the list.", required = true) @Valid @RequestParam(value = "type", required = true) String type,@ApiParam(value = "Whether or not to replace any existing list of this name.") @Valid @RequestParam(value = "replaceExisting", required = false) Boolean replaceExisting,@ApiParam(value = "A disambiguating value (such as organism name).") @Valid @RequestParam(value = "extraValue", required = false) String extraValue,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListUploadService listUploadService = new ListUploadService(im, getFormat(), body);
        try {
            listUploadService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListsPost listsPost = listUploadService.getListsPost();
        if(format.equals("json")) {
            setFooter(listsPost);
            return new ResponseEntity<ListsPost>(listsPost, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listTagsGet(@ApiParam(value = "The name of a list whose tags to retrieve. If no list is provided, then all the tags associated with the authenticating user will be returned.") @Valid @RequestParam(value = "name", required = false) String name, @ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListTagService listTagService = new ListTagService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            listTagService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Tags listTags = listTagService.getTagsModel();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(listTags);
            return new ResponseEntity<Tags>(listTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(listTags.getTags(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listTagsPost(@NotNull @ApiParam(value = "The name of a list to add the tag(s) to..", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of the tags to add. It should take to from of a semi-colon delimited concatenation of the tag names.", required = true) @Valid @RequestParam(value = "tags", required = true) String tags,@ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListTagAddingService listTagAddingService = new ListTagAddingService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            listTagAddingService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Tags listTags = listTagAddingService.getTagsModel();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(listTags);
            return new ResponseEntity<Tags>(listTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(listTags.getTags(),httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listTagsDelete(@NotNull @ApiParam(value = "The name of a list to add the tag(s) to.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of the tags to remove. It should take to from of a semi-colon delimited concatenation of the tag names.", required = true) @Valid @RequestParam(value = "tags", required = true) String tags,@ApiParam(value = "", allowableValues = "xml, json, tab, csv") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListTagRemovalService listTagRemovalService = new ListTagRemovalService(im, WebServiceRequestParser.interpretFormat(format), name);
        try {
            listTagRemovalService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Tags listTags = listTagRemovalService.getTagsModel();
        if(format.equals("json")) {
            ResponseUtilSpring.setJSONHeader(httpHeaders, "tags" + ".json", false);
            setFooter(listTags);
            return new ResponseEntity<Tags>(listTags, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>(listTags.getTags(),httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON || format == Format.TEXT;
    }


    /*
    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON
                || format == Format.HTML
                || format == Format.TEXT
                || Format.FLAT_FILES.contains(format);
    }
    */

}

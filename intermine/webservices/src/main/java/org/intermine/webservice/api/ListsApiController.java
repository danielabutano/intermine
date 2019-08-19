package org.intermine.webservice.api;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.JaccardIndex;
import org.intermine.webservice.model.ListAppend;
import org.intermine.webservice.model.ListInvitationMultiple;
import org.intermine.webservice.model.ListInvitationSingle;
import org.intermine.webservice.model.ListOperations;
import org.intermine.webservice.model.ListRename;
import org.intermine.webservice.model.ListSharingGet;
import org.intermine.webservice.model.ListSharingPost;
import org.intermine.webservice.model.ListsDelete;
import org.intermine.webservice.model.ListsGet;
import org.intermine.webservice.model.ListsPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.model.SimpleJsonModel;
import org.intermine.webservice.model.Tags;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.WebServiceRequestParser;
import org.intermine.webservice.server.lists.*;
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
import java.math.BigDecimal;
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

    public ResponseEntity<?> listsDifferencePost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name, @NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "lists", required = true) List<String> lists, @ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description, @ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags, @ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListDifferenceService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    public ResponseEntity<?> listsIntersectPost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "lists", required = true) List<String> lists,@ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListIntersectionService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    public ResponseEntity<?> listsSubtractGet(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "references", required = true) List<String> references,@NotNull @ApiParam(value = "The name of a list to exclude.", required = true) @Valid @RequestParam(value = "subtract", required = true) List<String> subtract,@ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListSubtractionService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    public ResponseEntity<?> listsSubtractPost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "references", required = true) List<String> references,@NotNull @ApiParam(value = "The name of a list to exclude.", required = true) @Valid @RequestParam(value = "subtract", required = true) List<String> subtract,@ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListSubtractionService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    public ResponseEntity<?> listsUnionGet(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "lists", required = true) List<String> lists,@ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListUnionService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    public ResponseEntity<?> listsUnionPost(@NotNull @ApiParam(value = "The name of the list to create.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "The name of a source list, or multiple list names concatenated with a ';' separator.", required = true) @Valid @RequestParam(value = "lists", required = true) List<String> lists,@ApiParam(value = "A description of this new list.") @Valid @RequestParam(value = "description", required = false) String description,@ApiParam(value = "A set of tags to apply to the new list.") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "", allowableValues = "json, text") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();
        setHeaders();
        return serveListOperations(new ListUnionService(im, WebServiceRequestParser.interpretFormat(format)), format);
    }

    private ResponseEntity<?> serveListOperations(ListOperationService listOperationService, String format){
        try {
            listOperationService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListOperations listOperations = listOperationService.getListOperations();
        if(format.equals("json")) {
            setFooter(listOperations);
            return new ResponseEntity<ListOperations>(listOperations, httpHeaders,httpStatus);
        }
        return new ResponseEntity<Object>("",httpHeaders,httpStatus);
    }

    public ResponseEntity<JaccardIndex> jaccardIndexGet(@ApiParam(value = "The name of the list.") @Valid @RequestParam(value = "list", required = false) String list, @ApiParam(value = "The list of InterMine IDs.") @Valid @RequestParam(value = "ids", required = false) String ids, @ApiParam(value = "If the Jaccard Index is lower than this value, discard.") @Valid @RequestParam(value = "min", required = false, defaultValue = "0.05") BigDecimal min, @ApiParam(value = "The type of InterMine objects (if providing IDs).") @Valid @RequestParam(value = "type", required = false) String type) {
        initController();
        return serveJaccardIndex(list, ids, min, type);
    }

    public ResponseEntity<JaccardIndex> jaccardIndexPost(@ApiParam(value = "The name of the list.") @Valid @RequestParam(value = "list", required = false) String list, @ApiParam(value = "The list of InterMine IDs.") @Valid @RequestParam(value = "ids", required = false) String ids, @ApiParam(value = "If the Jaccard Index is lower than this value, discard.") @Valid @RequestParam(value = "min", required = false, defaultValue = "0.05") BigDecimal min, @ApiParam(value = "The type of InterMine objects (if providing IDs).") @Valid @RequestParam(value = "type", required = false) String type) {
        initController();
        return serveJaccardIndex(list, ids, min, type);
    }

    private ResponseEntity<JaccardIndex> serveJaccardIndex(String listName, String ids, BigDecimal min, String type){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        JaccardIndexService jaccardIndexService = new JaccardIndexService(im, getFormat(), listName, ids, min, type);
        try {
            jaccardIndexService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        JaccardIndex jaccardIndex = jaccardIndexService.getJaccardIndexModel();
        setFooter(jaccardIndex);
        return new ResponseEntity<JaccardIndex>(jaccardIndex,httpHeaders,httpStatus);
    }

    public ResponseEntity<?> listsWithObjectGet(@ApiParam(value = "A stable identifier that can be used to find the object.") @Valid @RequestParam(value = "publicId", required = false) String publicId,@ApiParam(value = "The internal DB id (changes on each re-release).") @Valid @RequestParam(value = "id", required = false) Integer id,@ApiParam(value = "The type of object (required if using a public id).") @Valid @RequestParam(value = "type", required = false) String type,@ApiParam(value = "An extra value to disambiguate objects.") @Valid @RequestParam(value = "extraValue", required = false) String extraValue,@ApiParam(value = "", allowableValues = "json, html, text, csv, tab") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveListswithObjects(format);
    }

    public ResponseEntity<?> listsWithObjectPost(@ApiParam(value = "A stable identifier that can be used to find the object.") @Valid @RequestParam(value = "publicId", required = false) String publicId,@ApiParam(value = "The internal DB id (changes on each re-release).") @Valid @RequestParam(value = "id", required = false) Integer id,@ApiParam(value = "The type of object (required if using a public id).") @Valid @RequestParam(value = "type", required = false) String type,@ApiParam(value = "An extra value to disambiguate objects.") @Valid @RequestParam(value = "extraValue", required = false) String extraValue,@ApiParam(value = "", allowableValues = "json, html, text, csv, tab") @Valid @RequestParam(value = "format", required = false, defaultValue = "json") String format) {
        initController();
        return serveListswithObjects(format);
    }

    private ResponseEntity<?> serveListswithObjects(String format){
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListsService listsService = new ListsService(im, getFormat());
        try {
            listsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListsGet listsGet = listsService.getListsGet();
        if(format.equals("json")) {
            setFooter(listsGet);
            return new ResponseEntity<ListsGet>(listsGet, httpHeaders,httpStatus);
        } else if(format.equals("text")) {
            ResponseUtilSpring.setPlainTextHeader(httpHeaders, FILE_BASE_NAME + ".tsv");
        } else if(format.equals("tab")) {
            ResponseUtilSpring.setTabHeader(httpHeaders, FILE_BASE_NAME + ".tsv");
        } else if(format.equals("csv")) {
            ResponseUtilSpring.setCSVHeader(httpHeaders, FILE_BASE_NAME + ".csv");
        }
        return new ResponseEntity<Object>(listsGet.getLists(),httpHeaders,httpStatus);
    }

    public ResponseEntity<ListInvitationMultiple> listInvitationsGet() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListSharingInvitationDetailsService listSharingInvitationDetailsService = new ListSharingInvitationDetailsService(im, format, "");
        try {
            listSharingInvitationDetailsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListInvitationMultiple listInvitationMultiple = listSharingInvitationDetailsService.getListInvitationMultiple();
        setFooter(listInvitationMultiple);

        return new ResponseEntity<ListInvitationMultiple>(listInvitationMultiple,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListInvitationSingle> listInvitationsPost(@NotNull @ApiParam(value = "The list of yours you wish to share.", required = true) @Valid @RequestParam(value = "list", required = true) String list, @ApiParam(value = "The email address of the user to invite to share a list.") @Valid @RequestParam(value = "to", required = false) String to, @ApiParam(value = "Whether or not to send an email to the invitee. The invitee value must be an email address if true.") @Valid @RequestParam(value = "notify", required = false, defaultValue = "false") Boolean notify) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListSharingInvitationService listSharingInvitationService = new ListSharingInvitationService(im, format, list, to, notify);
        try {
            listSharingInvitationService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListInvitationSingle listInvitationSingle = listSharingInvitationService.getListInvitationSingle();
        setFooter(listInvitationSingle);

        return new ResponseEntity<ListInvitationSingle>(listInvitationSingle,httpHeaders,httpStatus);
    }

    public ResponseEntity<SimpleJsonModel> listSharesDelete(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "list", required = true) String list, @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "with", required = true) String with) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListShareDeletionService listShareDeletionService = new ListShareDeletionService(im, format, list, with);
        try {
            listShareDeletionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SimpleJsonModel simpleJsonModel = new SimpleJsonModel();
        setFooter(simpleJsonModel);

        return new ResponseEntity<SimpleJsonModel>(simpleJsonModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListSharingGet> listSharesGet() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListShareDetailsService listShareDetailsService = new ListShareDetailsService(im, format);
        try {
            listShareDetailsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListSharingGet listSharingGet = listShareDetailsService.getListSharingGet();
        setFooter(listSharingGet);

        return new ResponseEntity<ListSharingGet>(listSharingGet,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListSharingPost> listSharesPost(@NotNull @ApiParam(value = "The list of yours you wish to share.", required = true) @Valid @RequestParam(value = "list", required = true) String list, @NotNull @ApiParam(value = "The username of the user who will have access.", required = true) @Valid @RequestParam(value = "with", required = true) String with, @ApiParam(value = "Whether or not to send an email to the user you are sharing with.") @Valid @RequestParam(value = "notify", required = false, defaultValue = "false") Boolean notify) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListShareCreationService listShareCreationService = new ListShareCreationService(im, format, list, with, notify);
        try {
            listShareCreationService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListSharingPost listSharingPost = listShareCreationService.getListSharingPost();
        setFooter(listSharingPost);

        return new ResponseEntity<ListSharingPost>(listSharingPost,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListInvitationSingle> listsInvitationsUidGet(@ApiParam(value = "The identifier of the invitation - a 20 character unique string.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListSharingInvitationDetailsService listSharingInvitationDetailsService = new ListSharingInvitationDetailsService(im, format, uid);
        try {
            listSharingInvitationDetailsService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListInvitationSingle listInvitationSingle = listSharingInvitationDetailsService.getListInvitationSingle();
        setFooter(listInvitationSingle);

        return new ResponseEntity<ListInvitationSingle>(listInvitationSingle,httpHeaders,httpStatus);
    }

    public ResponseEntity<ListInvitationSingle> listsInvitationsUidPut(@ApiParam(value = "The identifier of the invitation - a 20 character unique string.",required=true) @PathVariable("uid") String uid,@NotNull @ApiParam(value = "Whether or not this invitation is accepted or not.", required = true) @Valid @RequestParam(value = "accepted", required = true) Boolean accepted) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ListSharingInvitationAcceptanceService listSharingInvitationAcceptanceService = new ListSharingInvitationAcceptanceService(im, format, uid, accepted);
        try {
            listSharingInvitationAcceptanceService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        ListInvitationSingle listInvitationSingle = listSharingInvitationAcceptanceService.getListInvitationSingle();
        setFooter(listInvitationSingle);

        return new ResponseEntity<ListInvitationSingle>(listInvitationSingle,httpHeaders,httpStatus);
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON
                || format == Format.HTML
                || format == Format.TEXT
                || Format.FLAT_FILES.contains(format);
    }


}

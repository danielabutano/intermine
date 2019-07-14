package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.DeregistrationToken;
import org.intermine.webservice.model.PermanentToken;
import org.intermine.webservice.model.Preferences;
import org.intermine.webservice.model.SimpleJsonModel;
import org.intermine.webservice.model.Token;
import org.intermine.webservice.model.Tokens;
import org.intermine.webservice.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.model.WhoAmI;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.user.DeletePreferencesService;
import org.intermine.webservice.server.user.DeleteTokensService;
import org.intermine.webservice.server.user.DeletionTokenCancellationService;
import org.intermine.webservice.server.user.DeletionTokenInfoService;
import org.intermine.webservice.server.user.DeregistrationService;
import org.intermine.webservice.server.user.NewDeletionTokenService;
import org.intermine.webservice.server.user.NewUserService;
import org.intermine.webservice.server.user.PermaTokenDeletionService;
import org.intermine.webservice.server.user.PermaTokenInfoService;
import org.intermine.webservice.server.user.ReadPreferencesService;
import org.intermine.webservice.server.user.SetPreferencesService;
import org.intermine.webservice.server.user.TokenService;
import org.intermine.webservice.server.user.TokensService;
import org.intermine.webservice.server.user.WhoAmIService;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
@Controller
public class UsersApiController extends InterMineController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private static final String FILE_BASE_NAME = "data";

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        super(objectMapper, request);
    }

    public ResponseEntity<Users> users(@NotNull @ApiParam(value = "The user name of the new user. It should be an email address if possible. There must not be any user with the same username.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "A password to associate with the account.", required = true) @Valid @RequestParam(value = "password", required = true) String password,@ApiParam(value = "Whether or not to subscribe to the mine's mailing list, if it has one. The username must be an email address if true.") @Valid @RequestParam(value = "subscribe-to-list", required = false) Boolean subscribeToList) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        NewUserService newUserService = new NewUserService(im, format);
        try {
            newUserService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Users users = newUserService.getUsers();
        setFooter(users);

        return new ResponseEntity<Users>(users,httpHeaders,httpStatus);
    }

    public ResponseEntity<WhoAmI> whoAmI() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        WhoAmIService whoAmIService = new WhoAmIService(im, format);
        try {
            whoAmIService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        WhoAmI whoAmI = whoAmIService.getWhoAmI();
        setFooter(whoAmI);

        return new ResponseEntity<WhoAmI>(whoAmI,httpHeaders,httpStatus);
    }

    public ResponseEntity<Token> token() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TokenService tokenService = new TokenService(im, format, "day", null);
        try {
            tokenService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Token token = tokenService.getToken();
        setFooter(token);

        return new ResponseEntity<Token>(token,httpHeaders,httpStatus);
    }

    public ResponseEntity<DeregistrationToken> deregistrationTokenDelete(@ApiParam(value = "The identifier of the token.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DeletionTokenCancellationService deletionTokenCancellationService = new DeletionTokenCancellationService(im, format, uid);
        try {
            deletionTokenCancellationService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        DeregistrationToken deregistrationToken = deletionTokenCancellationService.getDeregistrationToken();
        setFooter(deregistrationToken);

        return new ResponseEntity<DeregistrationToken>(deregistrationToken,httpHeaders,httpStatus);
    }

    public ResponseEntity<DeregistrationToken> deregistrationTokenGet(@ApiParam(value = "The identifier of the token.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DeletionTokenInfoService deletionTokenInfoService = new DeletionTokenInfoService(im, format, uid);
        try {
            deletionTokenInfoService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        DeregistrationToken deregistrationToken = deletionTokenInfoService.getDeregistrationToken();
        setFooter(deregistrationToken);

        return new ResponseEntity<DeregistrationToken>(deregistrationToken,httpHeaders,httpStatus);
    }

    public ResponseEntity<DeregistrationToken> deregistrationTokenPost() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        NewDeletionTokenService newDeletionTokenService = new NewDeletionTokenService(im, format);
        try {
            newDeletionTokenService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        DeregistrationToken deregistrationToken = newDeletionTokenService.getDeregistrationToken();
        setFooter(deregistrationToken);

        return new ResponseEntity<DeregistrationToken>(deregistrationToken,httpHeaders,httpStatus);
    }

    public ResponseEntity<Object> userDelete(@NotNull @ApiParam(value = "A token to ensure this is not a mistake.", required = true) @Valid @RequestParam(value = "deregistrationToken", required = true) String deregistrationToken) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DeregistrationService deregistrationService = new DeregistrationService(im, format, deregistrationToken);
        try {
            deregistrationService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Object user = deregistrationService.getUser();

        ResponseUtilSpring.setXMLHeader(httpHeaders, FILE_BASE_NAME + ".xml");
        return new ResponseEntity<Object>(user,httpHeaders,httpStatus);
    }

    public ResponseEntity<WhoAmI> userGet() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        WhoAmIService whoAmIService = new WhoAmIService(im, format);
        try {
            whoAmIService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        WhoAmI whoAmI = whoAmIService.getWhoAmI();
        setFooter(whoAmI);

        return new ResponseEntity<WhoAmI>(whoAmI,httpHeaders,httpStatus);
    }

    public ResponseEntity<Preferences> userPreferencesDelete(@ApiParam(value = "The preference to delete. If not provided, ALL will be cleared") @Valid @RequestParam(value = "key", required = false) String key) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DeletePreferencesService deletePreferencesService = new DeletePreferencesService(im, format, key);
        try {
            deletePreferencesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Preferences preferencesModel = deletePreferencesService.getPreferencesModel();
        setFooter(preferencesModel);

        return new ResponseEntity<Preferences>(preferencesModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<Preferences> userPreferencesGet() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        ReadPreferencesService readPreferencesService = new ReadPreferencesService(im, format);
        try {
            readPreferencesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Preferences preferencesModel = readPreferencesService.getPreferencesModel();
        setFooter(preferencesModel);

        return new ResponseEntity<Preferences>(preferencesModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<Preferences> userPreferencesPost(@ApiParam(value = "The preference to set.") @Valid @RequestParam(value = "preferences", required = false) Map<String, String> preferences) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SetPreferencesService setPreferencesService = new SetPreferencesService(im, format);
        try {
            setPreferencesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Preferences preferencesModel = setPreferencesService.getPreferencesModel();
        setFooter(preferencesModel);

        return new ResponseEntity<Preferences>(preferencesModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<Preferences> userPreferencesPut(@ApiParam(value = "The preference to set.") @Valid @RequestParam(value = "preferences", required = false) Map<String, String> preferences) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        SetPreferencesService setPreferencesService = new SetPreferencesService(im, format);
        try {
            setPreferencesService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Preferences preferencesModel = setPreferencesService.getPreferencesModel();
        setFooter(preferencesModel);

        return new ResponseEntity<Preferences>(preferencesModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<SimpleJsonModel> permanentTokenDelete(@ApiParam(value = "The identifier of one of your tokens.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        PermaTokenDeletionService permaTokenDeletionService = new PermaTokenDeletionService(im, format, uid);
        try {
            permaTokenDeletionService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SimpleJsonModel simpleJsonModel = new SimpleJsonModel();
        setFooter(simpleJsonModel);

        return new ResponseEntity<SimpleJsonModel>(simpleJsonModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<PermanentToken> permanentTokensGet(@ApiParam(value = "The identifier of one of your tokens.",required=true) @PathVariable("uid") String uid) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        PermaTokenInfoService permaTokenInfoService = new PermaTokenInfoService(im, format, uid);
        try {
            permaTokenInfoService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        PermanentToken permanentToken = permaTokenInfoService.getPermanentTokenModel();
        setFooter(permanentToken);

        return new ResponseEntity<PermanentToken>(permanentToken,httpHeaders,httpStatus);
    }

    public ResponseEntity<SimpleJsonModel> userTokensDelete() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        DeleteTokensService deleteTokensService = new DeleteTokensService(im, format);
        try {
            deleteTokensService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        SimpleJsonModel simpleJsonModel = new SimpleJsonModel();
        setFooter(simpleJsonModel);

        return new ResponseEntity<SimpleJsonModel>(simpleJsonModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<Tokens> userTokensGet() {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TokensService tokensService = new TokensService(im, format);
        try {
            tokensService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Tokens tokensModel = tokensService.getTokensModel();
        setFooter(tokensModel);

        return new ResponseEntity<Tokens>(tokensModel,httpHeaders,httpStatus);
    }

    public ResponseEntity<Token> userTokensPost(@ApiParam(value = "The type of token to issue.", allowableValues = "day, once, api, perm") @Valid @RequestParam(value = "type", required = false, defaultValue = "day") String type,@ApiParam(value = "An optional message to associate with a token.") @Valid @RequestParam(value = "message", required = false) String message) {
        initController();
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        setHeaders();
        TokenService tokenService = new TokenService(im, format, type, message);
        try {
            tokenService.service(request);
        } catch (Throwable throwable) {
            sendError(throwable);
        }
        Token token = tokenService.getToken();
        setFooter(token);

        return new ResponseEntity<Token>(token,httpHeaders,httpStatus);
    }


    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

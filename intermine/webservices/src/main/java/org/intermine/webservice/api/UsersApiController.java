package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Token;
import org.intermine.webservice.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.model.WhoAmI;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.user.NewUserService;
import org.intermine.webservice.server.user.TokenService;
import org.intermine.webservice.server.user.WhoAmIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
@Controller
public class UsersApiController extends InterMineController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

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
        TokenService tokenService = new TokenService(im, format);
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

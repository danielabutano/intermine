package org.intermine.webservice.api;

import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.webservice.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.intermine.webservice.server.user.NewUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
@Controller
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatus;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Users> users(@NotNull @ApiParam(value = "The user name of the new user. It should be an email address if possible. There must not be any user with the same username.", required = true) @Valid @RequestParam(value = "name", required = true) String name,@NotNull @ApiParam(value = "A password to associate with the account.", required = true) @Valid @RequestParam(value = "password", required = true) String password,@ApiParam(value = "Whether or not to subscribe to the mine's mailing list, if it has one. The username must be an email address if true.") @Valid @RequestParam(value = "subscribe-to-list", required = false) Boolean subscribeToList) {
        final InterMineAPI im = InterMineContext.getInterMineAPI();

        NewUserService newUserService = new NewUserService(im);
        try {
            newUserService.service(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        newUserService.setFooter();
        Users users = newUserService.getUsers();
        httpHeaders = newUserService.getResponseHeaders();
        httpStatus = newUserService.getHttpStatus();

        return new ResponseEntity<Users>(users,httpHeaders,httpStatus);
    }

}

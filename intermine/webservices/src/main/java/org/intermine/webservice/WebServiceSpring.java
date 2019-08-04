package org.intermine.webservice;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.api.util.AnonProfile;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.RequestUtil;
import org.intermine.web.logic.export.Exporter;
import org.intermine.web.logic.profile.PermissionHandler;
import org.intermine.web.security.KeyStorePublicKeySource;
import org.intermine.web.security.PublicKeySource;
import org.intermine.webservice.server.ColumnHeaderStyle;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.JWTVerifier;
import org.intermine.webservice.server.WebServiceRequestParser;
import org.intermine.webservice.server.core.ListManager;
import org.intermine.webservice.server.exceptions.BadRequestException;
import org.intermine.webservice.server.exceptions.MissingParameterException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;
import org.intermine.webservice.server.exceptions.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

public class WebServiceSpring {

    /** Default jsonp callback **/
    public static final String DEFAULT_CALLBACK = "callback";

    private static final String COMPRESS = "compress";
    private static final String GZIP = "gzip";
    private static final String ZIP = "zip";

    private static final Logger LOG = Logger.getLogger(WebServiceSpring.class);
    private static final String AUTHENTICATION_FIELD_NAME = "Authorization";
    private static final String AUTH_TOKEN_PARAM_KEY = "token";
    private static final Profile ANON_PROFILE = new AnonProfile();

    /**
     * The servlet request.
     */
    protected HttpServletRequest request;

    /**
     * The configuration object.
     */
    protected final InterMineAPI im;

    /** The properties this mine was configured with **/
    protected final Properties webProperties = InterMineContext.getWebProperties();
    private ProfileManager.ApiPermission permission = ProfileManager.getDefaultPermission(ANON_PROFILE);
    private boolean initialised = false;
    private String propertyNameSpace = null;

    private Format format;
    private Boolean isJsonP = null;

    /**
     * Construct the web service with the InterMine API object that gives access
     * to the core InterMine functionality.
     *
     * @param im
     *            the InterMine application
     * @param format
     */
    public WebServiceSpring(InterMineAPI im, Format format) {
        this.im = im;
        this.format = format;
    }

    /**
     * Get a configuration property by name.
     *
     * @param name
     *            The name of the property to retrieve.
     * @return A configuration value.
     */
    protected String getProperty(String name) {
        if (StringUtils.contains(name, '.')) {
            return webProperties.getProperty(name);
        }
        return webProperties.getProperty(propertyNameSpace == null ? name
                : propertyNameSpace + "." + name);
    }

    /**
     * Starting method of web service. The web service should be run like
     *
     * <pre>
     * new ListsService().service(request, response);
     * </pre>
     *
     * Ensures initialisation of web service and makes steps common for all web
     * services and after that executes the <tt>execute</tt> method, for which
     * each subclass must provide an implementation.
     *
     * @param request
     *            The request, as received by the servlet.
     */
    public void service(HttpServletRequest request) throws Throwable {
        this.request = request;
        try {
            initState();
            authenticate();
            initialised = true;
            validateState();
            execute();
        } catch (Throwable t) {
            throw t;
        }

    }

    private String lineBreak = null;

    /**
     * @return The line separator for the client's platform.
     */
    public String getLineBreak() {
        if (lineBreak == null && request != null) {
            if (RequestUtil.isWindowsClient(request)) {
                lineBreak = Exporter.WINDOWS_SEPARATOR;
            } else {
                lineBreak = Exporter.UNIX_SEPARATOR;
            }
        }
        return lineBreak;
    }


    /**
     * Subclasses can put initialisation here.
     */
    protected void initState() {
        // No-op stub
    }

    /**
     * Subclasses can put initialisation checks here. The main use case is for
     * confirming authentication.
     */
    protected void validateState() {
        // No-op stub
    }

    /**
     * @return Whether or not the requested result format is one of our JSON
     *         formats.
     */
    protected final boolean formatIsJSON() {
        return Format.JSON_FORMATS.contains(getFormat());
    }

    /**
     * @return Whether or not the format is a JSON-P format
     */
    protected final boolean formatIsJSONP() {
        if (isJsonP == null) {
            isJsonP = WebServiceRequestParser.isJsonP(request);
        }
        return isJsonP;
    }

    /**
     * @return Whether or not the format is a flat-file format
     */
    protected final boolean formatIsFlatFile() {
        return Format.FLAT_FILES.contains(getFormat());
    }

    /**
     * @return Whether or not the format is XML.
     */
    public boolean formatIsXML() {
        return (getFormat() == Format.XML);
    }

    /**
     * Returns true if the request wants column headers as well as result rows
     *
     * @return true if the request declares it wants column headers
     */
    public boolean wantsColumnHeaders() {
        String wantsCols = request
                .getParameter(WebServiceRequestParser.ADD_HEADER_PARAMETER);
        // Assume none wanted if empty
        boolean no = (wantsCols == null || wantsCols.isEmpty()
                // interpret standard falsy values as false
                || "0".equals(wantsCols) || "false".equalsIgnoreCase(wantsCols)
                // but none is what we really expect.
                || "none".equalsIgnoreCase(wantsCols));
        // All other values, including "true", "True", 1, and foo-bar are yes
        return !no;
    }

    /**
     * Get an enum which represents the column header style (path, friendly, or
     * none)
     *
     * @return a column header style
     */
    public ColumnHeaderStyle getColumnHeaderStyle() {
        if (wantsColumnHeaders()) {
            String style = request
                    .getParameter(WebServiceRequestParser.ADD_HEADER_PARAMETER);
            if ("path".equalsIgnoreCase(style)) {
                return ColumnHeaderStyle.PATH;
            } else {
                return ColumnHeaderStyle.FRIENDLY;
            }
        } else {
            return ColumnHeaderStyle.NONE;
        }
    }

    /**
     * @return The default format constant for this service.
     */
    protected Format getDefaultFormat() {
        return Format.EMPTY;
    }




    /**
     * Returns required output format.
     *
     * Cannot be overridden.
     *
     * @return format
     */
    public final Format getFormat() {

        return format;
    }

    /**
     * For very picky services, you can just set it yourself, and say "s****w you requester".
     *
     * Use this with caution, and fall-back to getFormat(). Please.
     *
     * @param format The format you have decided this request really wants.
     */
    protected void setFormat(Format format) {
        this.format = format;
    }

    /**
     * @return Whether or not this request wants gzipped data.
     */
    protected boolean isGzip() {
        return GZIP.equalsIgnoreCase(request.getParameter(COMPRESS));
    }

    /**
     * @return Whether or not this request wants zipped data.
     */
    protected boolean isZip() {
        return ZIP.equalsIgnoreCase(request.getParameter(COMPRESS));
    }

    /**
     * @return the file-name extension for the result-set.
     */
    protected String getExtension() {
        if (isGzip()) {
            return ".gz";
        } else if (isZip()) {
            return ".zip";
        } else {
            return "";
        }
    }

    /**
     * Get the value of the callback parameter.
     *
     * @return The value, or null if this request type does not support this.
     */
    public String getCallback() {
        if (formatIsJSONP()) {
            return getOptionalParameter(
                    WebServiceRequestParser.CALLBACK_PARAMETER,
                    DEFAULT_CALLBACK);
        } else {
            return null;
        }
    }

    /**
     * Determine whether a callback was supplied to this request.
     *
     * @return Whether or not a callback was supplied.
     */
    public boolean hasCallback() {
        return getOptionalParameter(WebServiceRequestParser.CALLBACK_PARAMETER) != null;
    }


    /**
     * Check whether the format is acceptable.
     *
     * By default returns true. Services with a particular set of accepted
     * formats should override this and check.
     * @param format The format to check.
     * @return whether or not this format is acceptable.
     */
    protected boolean canServe(Format format) {
        return format == getDefaultFormat();
    }

    private JWTVerifier.Verification getIdentityFromBearerToken(final String rawString) {
        JWTVerifier verifier;
        PublicKeySource keys;

        try {
            keys = new KeyStorePublicKeySource(InterMineContext.getKeyStore());
        } catch (KeyStoreException e) {
            throw new ServiceException("Failed to load key store.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Key store incorrectly configured", e);
        } catch (CertificateException e) {
            throw new ServiceException("Key store incorrectly configured", e);
        } catch (IOException e) {
            throw new ServiceException("Failed to load key store.", e);
        }
        try {
            verifier = new JWTVerifier(keys, webProperties);
            return verifier.verify(rawString);
        } catch (JWTVerifier.VerificationError e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private String getIdentityAssertion() {
        String header = webProperties.getProperty("authentication.identity.assertion.header");

        if (StringUtils.isNotBlank(header)) {
            return request.getHeader(header);
        }
        return null;
    }

    /**
     * Return the permission object representing the authorisation state of the
     * request. This is guaranteed to not be null.
     *
     * @return A permission object, from which a service may inspect the level
     *         of authorisation, and retrieve details about whom the request is
     *         authorised for.
     */
    protected ProfileManager.ApiPermission getPermission() {
        if (permission == null) {
            throw new IllegalStateException(
                    "There should always be a valid permission object");
        }
        return permission;
    }

    /** @return A ListManager for this user. **/
    protected ListManager getListManager() {
        return new ListManager(im, getPermission().getProfile());
    }


    /**
     * Get a parameter this service deems to be required.
     *
     * @param name The name of the parameter
     * @return The value of the parameter. Never null, never blank.
     * @throws MissingParameterException
     *             If the value of the parameter is blank or null.
     */
    protected String getRequiredParameter(String name) {
        String value = request.getParameter(name);
        if (StringUtils.isBlank(value)) {
            throw new MissingParameterException(name);
        }
        return value;
    }

    /**
     * Get a parameter this service deems to be optional, or the default value.
     *
     * @param name
     *            The name of the parameter.
     * @param defaultValue
     *            The default value.
     * @return The value provided, if there is a non-blank one, or the default
     *         value.
     */
    protected String getOptionalParameter(String name, String defaultValue) {
        String value = request.getParameter(name);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Get a parameter this service deems to be optional, or <code>null</code>.
     *
     * @param name
     *            The name of the parameter.
     * @return The value of the parameter, or <code>null</code>
     */
    protected String getOptionalParameter(String name) {
        return getOptionalParameter(name, null);
    }

    /**
     * Get the value of a parameter that should be interpreted as an integer.
     *
     * @param name The name of the parameter.
     * @return An integer
     * @throws BadRequestException if The value is absent or mal-formed.
     */
    protected Integer getIntParameter(String name) {
        String value = getRequiredParameter(name);
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            String msg = String.format("%s should be a valid number. Got %s", name, value);
            throw new BadRequestException(msg, e);
        }
    }

    /**
     * Get the value of a parameter that should be interpreted as an integer.
     *
     * @param name The name of the parameter.
     * @param defaultValue The value to return if none is provided by the user.
     * @return An integer
     * @throw BadRequestException if the user provided a mal-formed value.
     */
    protected Integer getIntParameter(String name, Integer defaultValue) {
        try {
            return getIntParameter(name);
        } catch (MissingParameterException e) {
            return defaultValue;
        }
    }

    /**
     * Get a profile that is a true authenticated user that exists in the
     * database.
     *
     * @return The user's profile.
     * @throws ServiceForbiddenException
     *             if this request resolves to an unauthenticated profile.
     */
    protected Profile getAuthenticatedUser() {
        Profile profile = getPermission().getProfile();
        if (profile.isLoggedIn()) {
            return profile;
        }
        throw new ServiceForbiddenException("You must be logged in to use this service");
    }

    /**
     * This method is responsible for setting the Permission for the current
     * request. It can be derived in a number of ways:
     *
     * <ul>
     *   <li>
     *     <h4>Basic Authentication</h3>
     *     Standard username and password stuff - best avoided.
     *   </li>
     *   <li>
     *     <h4>Token authentication</h4>
     *     User passes back an opaque token which has no meaning outside of this
     *     application. Recommended. The token can be either passed as the value of the
     *     <code>token</code> query parameter, or provided in the
     *     <code>Authorization</code> header with the string <code>"Token "</code>
     *     preceding it, i.e.:
     *     <code>Authorization: Token somelongtokenstring</code>
     *   </li>
     *   <li>
     *     <h4>JWT bearer tokens</h4>
     *     The user passes back a bearer token issued by someone we trust (could
     *     include ourselves). This requires the configuration of a keystore
     *     {@see KeyStoreBuilder}. Provides delegated authentication capabilities.
     *     Overkill for most users. The token must be provided in the <code>Authorization</code>
     *     header, preceded by the string <code>"Bearer "</code>, e.g.:
     *     <code>Authorization: Bearer yourjwttokenhere</code>
     *   </li>
     * </ul>
     *
     * {@link "http://en.wikipedia.org/wiki/Basic_access_authentication"}
     * {@link "http://jwt.io/"}
     */
    private void authenticate() {

        String authToken = request.getParameter(AUTH_TOKEN_PARAM_KEY);
        JWTVerifier.Verification identity = null;
        final String authString = request.getHeader(AUTHENTICATION_FIELD_NAME);
        final ProfileManager pm = im.getProfileManager();

        if (StringUtils.isEmpty(authToken) && StringUtils.isEmpty(authString)) {
            return; // Not Authenticated.
        }
        // Accept tokens passed in the Authorization header.
        if (StringUtils.isEmpty(authToken)) {
            if (StringUtils.startsWith(authString, "Token ")) {
                authToken = StringUtils.removeStart(authString, "Token ");
                try { // Allow bearer tokens to be passed in as normal tokens.
                    identity = getIdentityFromBearerToken(authToken);
                } catch (UnauthorizedException e) {
                    // pass - check the token below.
                }
            } else if (StringUtils.startsWith(authString, "Bearer ")) {
                identity = getIdentityFromBearerToken(
                        StringUtils.removeStart(authString, "Bearer "));
            } else {
                String identityAssertion = getIdentityAssertion();
                if (StringUtils.isNotBlank(identityAssertion)) {
                    identity = getIdentityFromBearerToken(identityAssertion);
                }
            }
        }

        try {
            // Use a token if provided.
            if (identity != null) {
                permission = pm.grantPermission(
                        identity.getIssuer(),
                        identity.getIdentity(),
                        im.getClassKeys());
            } else if (StringUtils.isNotEmpty(authToken)) {
                permission = pm.getPermission(authToken, im.getClassKeys());
            } else {
                // Try and read the authString as a basic auth header.
                // Strip off the "Basic" part - but don't require it.
                final String encoded = StringUtils.removeStart(authString, "Basic ");
                final String decoded = new String(Base64.decodeBase64(encoded.getBytes()));
                final String[] parts = decoded.split(":", 2);
                if (parts.length != 2) {
                    throw new UnauthorizedException(
                            "Invalid request authentication. "
                                    + "Authorization field contains invalid value. "
                                    + "Decoded authorization value: "
                                    + parts[0]);
                }
                // Allow tokens to be passed in basic auth headers.
                if (StringUtils.isEmpty(parts[1])) {
                    permission = pm.getPermission(parts[0], im.getClassKeys());
                } else {
                    final String username = StringUtils.lowerCase(parts[0]);
                    final String password = parts[1];

                    permission = pm.getPermission(username, password, im.getClassKeys());
                }
            }
        } catch (ProfileManager.AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        PermissionHandler.setUpPermission(im, permission);
    }

    /**
     * Runs service. Standard procedure is overwrite this
     * method in subclasses and let this method to be called from
     * WebService.doGet method that encapsulates logic common for all web
     * services else you can overwrite doGet method in your web service class
     * and manage all the things alone.
     *
     * @throws Exception
     *             if some error occurs
     */
    protected void execute() throws Exception {

    }

    /**
     * @return true if this request has been authenticated to a specific
     *         existing user.
     */
    public boolean isAuthenticated() {
        return getPermission().getProfile() != ANON_PROFILE;
    }


}

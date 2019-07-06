package org.intermine.webservice.server.user;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.HashMap;
import java.util.Map;

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.DeregistrationToken;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.ISO8601DateFormat;
import org.intermine.webservice.server.core.ReadWriteJSONService;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;

/**
 * A service that issues a deletion token to a user who intends to
 * delete their profile.
 * @author Alex Kalderimis
 *
 */
public class NewDeletionTokenService extends JSONServiceSpring
{
    private static final String DENIAL_MSG = "Access denied.";

    protected final DeletionTokens tokenFactory;

    public DeregistrationToken getDeregistrationToken() {
        return deregistrationToken;
    }

    private DeregistrationToken deregistrationToken;

    /**
     * @param im The InterMine state object.
     */
    public NewDeletionTokenService(InterMineAPI im, Format format) {
        super(im, format);
        this.tokenFactory = DeletionTokens.getInstance();
        deregistrationToken = new DeregistrationToken();
    }

    @Override
    protected String getResultsKey() {
        return "token";
    }

    @Override
    protected void execute() throws Exception {
        int lifeSpan = getIntParameter("validity", 60);
        Profile profile = getPermission().getProfile();
        DeletionToken token = tokenFactory.createToken(profile, lifeSpan);
        serveToken(token);
    }

    @Override
    protected void validateState() {
        if (!isAuthenticated() || getPermission().isRO()) {
            throw new ServiceForbiddenException(DENIAL_MSG);
        }
    }

    /**
     * Serve a token to the outside world.
     * @param token The token to return.
     */
    protected void serveToken(DeletionToken token) {
        Map<String, Object> info = new HashMap<String, Object>();

        info.put("uuid", token.getUUID().toString());
        info.put("expiry", ISO8601DateFormat.getFormatter().format(token.getExpiry()));
        info.put("secondsRemaining",
                (token.getExpiry().getTime() - System.currentTimeMillis()) / 1000);

        deregistrationToken.setToken(info);
    }

}

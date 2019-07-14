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

import java.util.Collections;

import org.intermine.api.InterMineAPI;
import org.intermine.api.userprofile.PermanentToken;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.ReadWriteJSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;

/**
 * @author Alex Kalderimis
 */
public class PermaTokenInfoService extends JSONServiceSpring {

    private static final String DENIAL_MSG = "Access denied.";

    private String uuid;

    public org.intermine.webservice.model.PermanentToken getPermanentTokenModel() {
        return permanentTokenModel;
    }

    private org.intermine.webservice.model.PermanentToken permanentTokenModel;

    /**
     * @param im The InterMine state object.
     * @param uuid The token we want to know more about.
     */
    public PermaTokenInfoService(InterMineAPI im, Format format, String uuid) {
        super(im, format);
        this.uuid = uuid;
        permanentTokenModel = new org.intermine.webservice.model.PermanentToken();
    }

    @Override
    protected void execute() throws Exception {
        PermanentToken token = new PermanentToken();
        token.setToken(uuid);
        token = (PermanentToken) im.getProfileManager()
                  .getProfileObjectStoreWriter()
                  .getObjectByExample(token, Collections.singleton("token"));
        if (token == null) {
            throw new ResourceNotFoundException(uuid + " is not a token");
        }
        permanentTokenModel.setToken(PermaTokens.format(token));
    }

    @Override
    public String getResultsKey() {
        return "token";
    }

    @Override
    protected void validateState() {
        if (!isAuthenticated() || getPermission().isRO()) {
            throw new ServiceForbiddenException(DENIAL_MSG);
        }
    }
}

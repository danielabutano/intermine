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
import org.intermine.objectstore.ObjectStoreWriter;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.ReadWriteJSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;

/**
 * Does what it says on the tin.
 * @author Alex Kalderimis
 */
public class PermaTokenDeletionService extends JSONServiceSpring {

    private static final String DENIAL_MSG = "Access denied.";

    private String uuid;

    /**
     * Constructor.
     * @param im The InterMine state object.
     * @param uuid The token we dislike so much.
     */
    public PermaTokenDeletionService(InterMineAPI im, Format format, String uuid) {
        super(im, format);
        this.uuid = uuid;
    }

    @Override
    protected void execute() throws Exception {
        PermanentToken token = new PermanentToken();
        token.setToken(uuid);
        ObjectStoreWriter osw = im.getProfileManager()
                  .getProfileObjectStoreWriter();
        token = (PermanentToken) osw.getObjectByExample(token, Collections.singleton("token"));
        if (token == null) {
            throw new ResourceNotFoundException(uuid + " is not a token");
        }
        im.getProfileManager().removePermanentToken(token);
    }

    @Override
    protected void validateState() {
        if (!isAuthenticated() || getPermission().isRO()) {
            throw new ServiceForbiddenException(DENIAL_MSG);
        }
    }
}

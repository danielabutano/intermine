package org.intermine.webservice.server.query;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.ReadWriteJSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;

/**
 * A service that removes a saved query from profile's collection of saved queries.
 * @author Alex Kalderimis
 *
 */
public class QueryRemovalService extends JSONServiceSpring
{
    private static final String DENIAL_MSG = "Access denied.";

    private String name;

    /** @param im The InterMine state object **/
    public QueryRemovalService(InterMineAPI im, Format format, String name) {
        super(im, format);
        this.name = name;
    }

    @Override
    protected void execute() {
        Profile p = getPermission().getProfile();
        if (!p.getSavedQueries().containsKey(name)) {
            throw new ResourceNotFoundException("Could not find query named " + name);
        }
        try {
            p.deleteQuery(name);
        } catch (Exception e) {
            throw new ServiceException("Could not delete query " + name);
        }
    }


    @Override
    protected boolean canServe(Format format) {
        switch (format) {
            case XML:
            case JSON:
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void validateState() {
        if (!isAuthenticated() || getPermission().isRO()) {
            throw new ServiceForbiddenException(DENIAL_MSG);
        }
    }

}

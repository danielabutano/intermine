package org.intermine.webservice.server;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.Session;
import org.intermine.webservice.server.core.JSONService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 *  Open a new 24-hour session.
 *
 *  For authenticated users this just issues a new 24hr token,
 *  but for unauthenticated users it assigns a new in-memory profile,
 *  allowing users to save lists
 *  and all that good authenticated stuff, without actually creating a user.
 *
 * @author Alex Kalderimis
 *
 */
public class SessionService extends JSONServiceSpring
{

    public Session getSession() {
        return session;
    }

    private Session session;

    /** @param im The InterMine state object **/
    public SessionService(InterMineAPI im) {
        super(im);
        session = new Session();
    }

    @Override
    protected String getResultsKey() {
        return "token";
    }

    @Override
    protected void execute() throws Exception {
        ProfileManager pm = im.getProfileManager();
        Profile p;
        if (isAuthenticated()) {
            p = getPermission().getProfile();
        } else {
            p = pm.createAnonymousProfile();
            p.disableSaving();
        }
        String token = pm.generate24hrKey(p);
        p.setDayToken(token);
        session.setToken(token);
    }

}

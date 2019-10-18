package org.intermine.web.logic.profile;

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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.BadTemplateException;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.api.profile.SavedQuery;
import org.intermine.api.template.ApiTemplate;
import org.intermine.api.util.NameUtil;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.web.logic.Constants;
import org.intermine.web.logic.WebServiceConstants;
import org.intermine.web.logic.session.SessionMethods;
import org.intermine.web.struts.InterMineAction;
import org.intermine.web.util.URLGenerator;
import org.json.JSONObject;

/**
 * @author Xavier Watkins
 *
 *
 * Abstract class containing the methods for login in and copying current
 * history, bags,... into profile.
 *
 */
public abstract class LoginHandler extends InterMineAction
{
    private static final Logger LOG = Logger.getLogger(LoginHandler.class);

    /**
     * Log-in a user.
     *
     * After this method completes, SessionMethods::getProfile will return this
     * user's profile.
     *
     * @param request The HttpServletRequest
     * @param username The username
     * @param password The password
     * @return the map containing the renamed bags the user created before they were logged in
     */
    public ProfileMergeIssues doLogin(HttpServletRequest request,
            String username, String password) {
        ProfileMergeIssues issues = doStaticLogin(request, username, password);
        HttpSession session = request.getSession();
        ProfileManager pm = SessionMethods.getInterMineAPI(session).getProfileManager();
        Profile profile = pm.getProfile(username);
        InterMineAPI im = SessionMethods.getInterMineAPI(session);
        if (im.getBagManager().isAnyBagNotCurrentOrUpgrading(profile)) {
            recordError(new ActionMessage("login.upgradeListStarted"), request);
        } else if (im.getBagManager().isAnyBagToUpgrade(profile)) {
            recordError(new ActionMessage("login.upgradeListManually"), request);
        }
        return issues;
    }

    /**
     * Merge two profiles together. This is mainly of use when a new user registers and we need
     * to save their current anonymous session into their new profile.
     * @param fromProfile The profile to take information from.
     * @param toProfile The profile to merge into.
     * @return A map of bags, from old name to new name.
     */
    public static ProfileMergeIssues mergeProfiles(Profile fromProfile, Profile toProfile) {
        Map<String, SavedQuery> mergeQueries = Collections.emptyMap();
        Map<String, InterMineBag> mergeBags = Collections.emptyMap();
        Map<String, ApiTemplate> mergeTemplates = Collections.emptyMap();
        ProfileMergeIssues issues = new ProfileMergeIssues();
        if (!fromProfile.getPreferences().isEmpty()) {
            toProfile.getPreferences().putAll(fromProfile.getPreferences());
        }
        if (fromProfile != null) {
            mergeQueries = fromProfile.getHistory();
            mergeBags = fromProfile.getSavedBags();
            mergeTemplates = fromProfile.getSavedTemplates();
        }

        // Merge in anonymous query history
        for (SavedQuery savedQuery : mergeQueries.values()) {
            toProfile.saveHistory(savedQuery);
        }
        // Merge in saved templates.
        for (ApiTemplate t: mergeTemplates.values()) {
            try {
                toProfile.saveTemplate(t.getName(), t);
            } catch (BadTemplateException e) {
                // Could be because the name is invalid - fix it.
                String oldName = t.getName();
                String newName = NameUtil.validateName(toProfile.getSavedBags().keySet(), oldName);
                try {
                    toProfile.saveTemplate(newName, t);
                    issues.addFailedTemplate(oldName, newName);
                } catch (BadTemplateException e1) {
                    // Template is bad for some other reason - should not happen.
                    // Currently the only reason we refuse to save templates is their name
                    // when/if other reasons are added, then we should record them on the issues.
                    LOG.error("Could not save template due to BadTemplateException", e1);
                }
            }
        }
        // Merge anonymous bags
        for (Map.Entry<String, InterMineBag> entry : mergeBags.entrySet()) {
            InterMineBag bag = entry.getValue();
            // Make sure the userId gets set to be the profile one
            try {
                bag.setProfileId(toProfile.getUserId());
                String name = NameUtil.validateName(toProfile.getSavedBags().keySet(),
                        entry.getKey());
                if (!entry.getKey().equals(name)) {
                    issues.addRenamedBag(entry.getKey(), name);
                }
                bag.setName(name);
                toProfile.saveBag(name, bag);
            } catch (ObjectStoreException iex) {
                throw new RuntimeException(iex.getMessage());
            }
        }
        return issues;
    }

    /**
     * Main log-in logic.
     * @param request The current request.
     * @param username The current user's login name.
     * @param password The current user's password.
     * @return A map of renamed-bags from old to new name.
     */
    public static ProfileMergeIssues doStaticLogin(HttpServletRequest request,
            String username, String password) {
        // Merge current history into loaded profile

        Profile currentProfile = SessionMethods.getProfile(request.getSession());

        ProfileMergeIssues issues = new ProfileMergeIssues();
/*
        if (currentProfile != null && StringUtils.isEmpty(currentProfile.getUsername())) {
            // The current profile was for an anonymous guest.
            issues = mergeProfiles(currentProfile, profile);
        }*/

        //call login webservice so same profile will be created in pm.limitatedaccesstoken
        Client client = ClientBuilder.newClient();
        try {
            String baseUrl = (new URLGenerator(request)).getPermanentBaseURL();
            String loginServiceUrl = baseUrl +  "/" +  WebServiceConstants.MODULE_NAME + "/login";
            WebTarget target = client.target(loginServiceUrl);
            Form form = new Form();
            form.param("username", username);
            form.param("password", password);
            String authToken = "Token " + currentProfile.getDayToken();
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .header("Authorization", authToken).post(Entity.form(form));
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                JSONObject result = new JSONObject(response.readEntity(String.class));
                String token = result.getString("token");
                //setUp in the session the new profile (loaded from db)
                Profile profile = setUpProfile(request.getSession(), username, password);
                profile.getProfileManager().addTokenForProfile(profile, token);
                profile.setDayToken(token);
            }
        } catch (RuntimeException ex) {
            LOG.error("Problems connecting to login web service");
        }
        return issues;
    }

    /**
     * Initialises a profile for the current user based on their user name and password.
     *
     * @param session HTTP session
     * @param username user name
     * @param password password
     * @return profile, fully ready to use.
     */
    public static Profile setUpProfile(HttpSession session, String username, String password) {
        final InterMineAPI im = SessionMethods.getInterMineAPI(session);
        Profile profile;
        ProfileManager pm = SessionMethods.getInterMineAPI(session).getProfileManager();
        if (pm.hasProfile(username)) {
            pm.updateCache(username, im.getClassKeys());
            profile = pm.getProfile(username, password, im.getClassKeys());
        } else {
            throw new LoginException("There is no profile for " + username);
        }
        return setUpProfile(session, profile);
    }


    /**
     * Sets up a profile ready for a session in InterMine.
     *
     * @param session http session
     * @param profile the user's profile (possibly anonymous and temporary)
     * @return profile The profile all cleaned up and good to go.
     */
    public static Profile setUpProfile(HttpSession session, Profile profile) {
        SessionMethods.setProfile(session, profile);
        final InterMineAPI im = SessionMethods.getInterMineAPI(session);
        if (profile.isSuperuser()) {
            session.setAttribute(Constants.IS_SUPERUSER, Boolean.TRUE);
        }
        UpgradeBagList upgrade = new UpgradeBagList(profile, im.getBagQueryRunner());
        UpgradeBagRunner.runBagUpgrade(upgrade, im, profile);
        return profile;
    }

    private static final class LoginException extends RuntimeException
    {

        /**
         * Default serial id.
         */
        private static final long serialVersionUID = 1L;

        private LoginException() {
            super();
        }

        private LoginException(String message) {
            super(message);
        }
    }
}

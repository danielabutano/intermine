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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.SavedQuery;
import org.intermine.api.profile.BadTemplateException;
import org.intermine.api.template.ApiTemplate;
import org.intermine.api.util.NameUtil;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.web.logic.profile.ProfileMergeIssues;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.UnauthorizedException;

import java.util.Collections;
import java.util.Map;

/**
 * Login service
 * @author Daniela Butano
 *
 */
public class LoginService extends JSONService
{
    private static final Logger LOG = Logger.getLogger(LoginService.class);

    /**
     * Constructor
     * @param im A reference to the InterMine API settings bundle
     */
    public LoginService(InterMineAPI im) {
        super(im);
    }

    @Override
    protected String getResultsKey() {
        return "token";
    }

    @Override
    protected void execute() throws Exception {
        Profile currentProfile = getPermission().getProfile();
        String username = getRequiredParameter("username");
        String password = getRequiredParameter("password");
        Profile profile = null;
        try {
            profile = getUser(username, password);
        } catch (ProfileManager.AuthenticationException ex) {
            throw new UnauthorizedException("username or password wrong");
        }
        ProfileMergeIssues issues = new ProfileMergeIssues();
        if (currentProfile != null && StringUtils.isEmpty(currentProfile.getUsername())) {
            // The current profile was for an anonymous guest.
            issues = mergeProfiles(currentProfile, profile);
        }
        String token = im.getProfileManager().generate24hrKey(profile);
        profile.setDayToken(token);
        addResultValue(token, false);
    }

    private Profile getUser(String username, String password) {
        ProfileManager pm = im.getProfileManager();
        if (pm.hasProfile(username)) {
            if (!pm.validPassword(username, password)) {
                throw new ProfileManager.AuthenticationException("Invalid password supplied");
            } else {
                Profile p = pm.getProfile(username, im.getClassKeys());
                return p;
            }
        } else {
            throw new ProfileManager.AuthenticationException("Unknown username: " + username);
        }
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


}

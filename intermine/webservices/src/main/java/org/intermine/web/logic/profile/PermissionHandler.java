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

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.BagState;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;

/**
 * Configure permission object for webservices
 *
 * @author Daniela Butano
 */
public abstract class PermissionHandler
{
    /**
     * Does whatever needs to be done to a permissions object to get it ready
     * for a life cyle in a web service request. At the moment, this just means
     * determining if this is the super user, and running the bag upgrade
     * thread.
     *
     * @param api
     *            The InterMine API object.
     * @param permission
     *            The permission that needs setting up.
     */
    public static void setUpPermission(InterMineAPI api,
                                       ProfileManager.ApiPermission permission) {
        final ProfileManager pm = api.getProfileManager();
        final Profile profile = permission.getProfile();
        final String userName = profile.getUsername();
        if (profile.isSuperuser() || (userName != null && userName.equals(pm.getSuperuser()))) {
            permission.addRole("SUPERUSER");
        }
        if (!api.getBagManager().isAnyBagInState(profile, BagState.UPGRADING)) {
            UpgradeBagList upgrade = new UpgradeBagList(profile, api.getBagQueryRunner());
            UpgradeBagRunner.runBagUpgrade(upgrade, api, profile);
        }
    }
}

package org.intermine.web.logic.profile;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.BagState;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.objectstore.intermine.ObjectStoreWriterInterMineImpl;
import org.intermine.sql.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Configure permission object for webservices
 */
public abstract class PermissionHandler {
    private static final Logger LOG = Logger.getLogger(PermissionHandler.class);

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

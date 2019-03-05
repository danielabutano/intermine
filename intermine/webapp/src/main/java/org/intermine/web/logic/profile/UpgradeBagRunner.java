package org.intermine.web.logic.profile;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.objectstore.intermine.ObjectStoreWriterInterMineImpl;
import org.intermine.sql.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Start upgrading bag for current user
 */
public abstract class UpgradeBagRunner {
    private static final Logger LOG = Logger.getLogger(UpgradeBagRunner.class);

    /**
     * Kick off a bag upgrade for current user.
     * @param procedure The bag upgrade routine.
     * @param api The InterMine state object.
     * @param profile The current user's profile.
     */
    public static void runBagUpgrade(
            UpgradeBagList procedure,
            InterMineAPI api,
            Profile profile) {
        Connection con = null;
        try {
            con = ((ObjectStoreWriterInterMineImpl) api.getProfileManager()
                    .getProfileObjectStoreWriter()).getDatabase()
                    .getConnection();
            if (api.getBagManager().isAnyBagNotCurrent(profile)
                    && !DatabaseUtil.isBagValuesEmpty(con)) {
                Thread upgrade = new Thread(procedure);
                upgrade.setDaemon(true);
                upgrade.start();
            }
        } catch (SQLException sqle) {
            LOG.error("Problems retrieving the connection", sqle);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
            }
        }
    }
}

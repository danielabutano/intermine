package org.intermine.webservicesspring.service;

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.ProfileManager;
import org.intermine.api.util.AnonProfile;

public class WebService {

    private static final Profile ANON_PROFILE = new AnonProfile();


    /**
     * The configuration object.
     */
    protected final InterMineAPI im;

    private ProfileManager.ApiPermission permission = ProfileManager.getDefaultPermission(ANON_PROFILE);


    /**
     * Construct the web service with the InterMine API object that gives access
     * to the core InterMine functionality.
     *
     * @param im
     *            the InterMine application
     */
    public WebService(InterMineAPI im) {
        this.im = im;
    }

    /**
     * Return the permission object representing the authorisation state of the
     * request. This is guaranteed to not be null.
     *
     * @return A permission object, from which a service may inspect the level
     *         of authorisation, and retrieve details about whom the request is
     *         authorised for.
     */
    protected ProfileManager.ApiPermission getPermission() {
        if (permission == null) {
            throw new IllegalStateException(
                    "There should always be a valid permission object");
        }
        return permission;
    }
}

package org.intermine.web.struts;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.intermine.api.InterMineAPI;
import org.intermine.api.config.ClassKeyHelper;
import org.intermine.api.profile.BagState;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.web.logic.session.SessionMethods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Update the profile with the new bag created via web-services.
 *
 * @author Daniela Butano
 */
public class UpdateProfileWithBagAction extends InterMineAction
{
    private static final Logger LOG = Logger.getLogger(UpdateProfileWithBagAction.class);
    /**
     * Update the profile
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return an ActionForward object defining where control goes next
     *
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        final InterMineAPI im = SessionMethods.getInterMineAPI(request.getSession());
        Profile profile = SessionMethods.getProfile(session);
        String bagName = request.getParameter("name");
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String osId = request.getParameter("osId");

        if (bagName == null || id == null || type == null) {
            LOG.error("Something wrong for the bag with name " + bagName  + ", id " + id + " and type " + type);
            return null;
        }
        if (profile.getSavedBags().get(bagName) == null) {
            if (profile.isLoggedIn()) {
                //upload the profile with the bag which has been already saved into the db by the ws
                InterMineBag bag = new InterMineBag(im.getObjectStore(), Integer.parseInt(id),
                        profile.getProfileManager().getProfileObjectStoreWriter());
                bag.setKeyFieldNames(ClassKeyHelper.getKeyFieldNames(
                        im.getClassKeys(), bag.getType()));
                bag.setProfileId(profile.getUserId());
                profile.saveBag(bagName, bag);
            } else {
                //anonymous
                InterMineBag bag = new InterMineBag(bagName, type, Integer.parseInt(id),
                        im.getObjectStore(), Integer.parseInt(osId));
                bag.setProfileId(profile.getUserId());
                profile.saveBag(bagName, bag);
            }
        }
        return null;
    }
}

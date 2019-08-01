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

import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.session.SessionMethods;

/**
 * Controller for the login page.
 *
 * @author Kim Rutherford
 */

public class LoginController extends TilesAction
{
    /**
     * Set up the form for login.
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(ComponentContext context,
                                 ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception {

        Properties webProperties = InterMineContext.getWebProperties();
        LoginForm loginForm = (LoginForm) form;
        String returnToString = request.getParameter("returnto");
        loginForm.setReturnToString(returnToString);
        request.getSession().setAttribute("returnTo", returnToString);

        //Handle Merge request from IM auth server
        try {
            if (request.getHeader("referer").contains(webProperties.getProperty("oauth2.IM.url.auth"))) {
                String sub = request.getParameter("sub");
                request.getSession().setAttribute("sub", sub);
            }
        }
        catch (NullPointerException e){
            System.out.print("This is not a Merge request");
        }

        Properties webprops = SessionMethods.getWebProperties(request);
        String ourPath = webprops.getProperty("webapp.baseurl");
        boolean isLocal = Pattern.compile(":\\d+").matcher(ourPath).find();
        request.setAttribute("isExternallyAccessible", !isLocal);

        return null;
    }
}

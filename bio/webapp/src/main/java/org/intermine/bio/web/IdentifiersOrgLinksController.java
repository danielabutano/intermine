package org.intermine.bio.web;

/*
 * Copyright (C) 2002-2020 FlyMine
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
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;

import org.intermine.api.uri.InterMineLUI;
import org.intermine.api.uri.InterMineLUIConverter;

import org.intermine.web.logic.results.ReportObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * Set up maps for attributeLinks.jsp
 * @author Daniela Butano
 */
public class IdentifiersOrgLinksController extends TilesAction
{

    protected static final Logger LOG = Logger.getLogger(IdentifiersOrgLinksController.class);

    @SuppressWarnings("serial")
    private class ConfigMap extends HashMap<String, Object>
    {
        // empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(ComponentContext context, ActionMapping mapping,
                                 ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {


        ReportObject reportObject = (ReportObject) request.getAttribute("reportObject");

        //identifiers-widget config
        InterMineLUIConverter converter = new InterMineLUIConverter();
        InterMineLUI interMineLUI = converter.getInterMineLUI(reportObject.getType(),
                reportObject.getId());
        String ns = "TBC";
        if (reportObject.getType().equals("Protein")) {
            ns = "uniprot";
        } else if (reportObject.getType().equals("Gene")) {
            ns = "fb";
        }
        String identifiersLUI = ns + ":" + interMineLUI.getIdentifier();
        request.setAttribute("identifiersLUI", identifiersLUI);
        return null;
    }
}

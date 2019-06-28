package org.intermine.webservice.server.fair;

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
import org.intermine.web.fair.SemanticMarkupUtil;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.SemanticMarkup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Serve datacatalog markup to be added to the home page
 * @author Daniela Butano
 *
 */
public class DataCatalogMarkupService extends JSONServiceSpring
{
    public SemanticMarkup getSemanticMarkup() {
        return semanticMarkup;
    }

    private SemanticMarkup semanticMarkup;

    /**
     * Constructor
     * @param im The InterMine state object.
     **/
    public DataCatalogMarkupService(InterMineAPI im) {
        super(im);
        semanticMarkup = new SemanticMarkup();
    }

    @Override
    protected void execute() throws Exception {
        if (SemanticMarkupUtil.isEnabled()) {
            semanticMarkup.setProperties(SemanticMarkupUtil.getDataCatalogMarkup(request));
        }
    }

    @Override
    public String getResultsKey() {
        return "properties";
    }

}

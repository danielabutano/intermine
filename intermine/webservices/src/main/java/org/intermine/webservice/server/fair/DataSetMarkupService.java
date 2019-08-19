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
import org.intermine.webservice.server.Format;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Serve dataset markup to be added to the report page
 * @author Daniela Butano
 *
 */
public class DataSetMarkupService extends JSONServiceSpring
{
    public SemanticMarkup getSemanticMarkup() {
        return semanticMarkup;
    }

    private SemanticMarkup semanticMarkup;

    /**
     * Constructor
     * @param im The InterMine state object.
     * @param format
     **/
    public DataSetMarkupService(InterMineAPI im, Format format) {
        super(im, format);
        semanticMarkup = new SemanticMarkup();
    }

    @Override
    protected void execute() throws Exception {
        String dataSetName = getRequiredParameter("name");
        String description = getOptionalParameter("description");
        String url = getOptionalParameter("url");
        semanticMarkup.setProperties(SemanticMarkupUtil.getDataSetMarkup(request, dataSetName, description, url));
    }

    @Override
    public String getResultsKey() {
        return "properties";
    }

}

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
import org.intermine.webservice.server.core.JSONService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Serve bioentity markup to be added to the report page
 * @author Daniela Butano
 *
 */
public class BioEntityMarkupService extends JSONServiceSpring
{
    public SemanticMarkup getSemanticMarkup() {
        return semanticMarkup;
    }

    private SemanticMarkup semanticMarkup;

    /**
     * Constructor
     * @param im The InterMine state object.
     **/
    public BioEntityMarkupService(InterMineAPI im) {
        super(im);
        semanticMarkup = new SemanticMarkup();
    }

    @Override
    protected void execute() throws Exception {
        String entityType = getRequiredParameter("type");
        int id = Integer.parseInt(getRequiredParameter("id"));
        semanticMarkup.setProperties(SemanticMarkupUtil.getBioEntityMarkup(request, entityType, id));
    }

    @Override
    public String getResultsKey() {
        return "properties";
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        semanticMarkup.setExecutionTime(executionTime);


        if (status >= 400) {
            semanticMarkup.setWasSuccessful(false);
            semanticMarkup.setError(escapeJava(errorMessage));
        } else {
            semanticMarkup.setWasSuccessful(true);
        }
        semanticMarkup.setStatusCode(status);
    }
}

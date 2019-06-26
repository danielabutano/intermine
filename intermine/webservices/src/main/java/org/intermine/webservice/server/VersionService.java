package org.intermine.webservice.server;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.intermine.api.InterMineAPI;
import org.intermine.web.logic.Constants;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.Version;
import org.intermine.webservice.server.output.HTMLTableFormatter;

/**
 * Service for returning the version of this service.
 * @author Alex Kalderimis
 *
 */
public class VersionService extends JSONServiceSpring
{
    public Version getVersion() {
        return version;
    }

    private Version version;

    /**
     * Constructor
     * @param im The InterMine configuration object.
     */
    public VersionService(InterMineAPI im) {
        super(im);
        version = new Version();
    }

    @Override
    protected void execute() throws Exception {
        version.setVersion(Constants.WEB_SERVICE_VERSION);
    }

    @Override
    protected Format getDefaultFormat() {
        if (hasCallback()) {
            return Format.JSON;
        } else {
            return Format.TEXT;
        }
    }

    @Override
    protected String getResultsKey() {
        return "version";
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.JSON
            || format == Format.HTML
            || format == Format.TEXT;
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        version.setExecutionTime(executionTime);


        if (status >= 400) {
            version.setWasSuccessful(false);
            version.setError(escapeJava(errorMessage));
        } else {
            version.setWasSuccessful(true);
        }
        version.setStatusCode(status);
    }
}

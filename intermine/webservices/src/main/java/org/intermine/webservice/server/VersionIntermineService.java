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
import org.intermine.webservice.model.VersionRelease;
import org.intermine.webservice.server.output.HTMLTableFormatter;

/**
 * Service for returning the version of this service.
 * @author Alex Kalderimis
 *
 */
public class VersionIntermineService extends JSONServiceSpring
{

    public VersionRelease getVersionRelease() {
        return versionRelease;
    }

    private VersionRelease versionRelease;

    /**
     * Constructor
     * @param im The InterMine configuration object.
     */
    public VersionIntermineService(InterMineAPI im) {
        super(im);
        versionRelease = new VersionRelease();
    }

    @Override
    protected void execute() throws Exception {
        versionRelease.setVersion(Constants.INTERMINE_VERSION);
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

}

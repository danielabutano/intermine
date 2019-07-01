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

import org.intermine.api.InterMineAPI;
import org.intermine.web.logic.Constants;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.Version;

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
     * @param format
     */
    public VersionService(InterMineAPI im, Format format) {
        super(im, format);
        version = new Version();
    }

    @Override
    protected void execute() throws Exception {
        version.setVersion(Constants.WEB_SERVICE_VERSION);
    }

    @Override
    protected String getResultsKey() {
        return "version";
    }

}

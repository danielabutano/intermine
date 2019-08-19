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
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.VersionRelease;

/**
 * Service for returning the version of this service.
 * @author Alex Kalderimis
 *
 */
public class VersionReleaseService extends JSONServiceSpring
{
    public VersionRelease getVersionRelease() {
        return versionRelease;
    }

    private VersionRelease versionRelease;

    /**
     * Constructor
     * @param im The InterMine configuration object.
     * @param format
     */
    public VersionReleaseService(InterMineAPI im, Format format) {
        super(im, format);
        versionRelease = new VersionRelease();
    }

    @Override
    protected void execute() throws Exception {
        versionRelease.setVersion(webProperties.getProperty("project.releaseVersion"));
    }

    @Override
    protected String getResultsKey() {
        return "version";
    }


}

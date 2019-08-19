package org.intermine.webservice.server.idresolution;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.api.idresolution.IDResolver;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;

/** @author Alex Kalderimis **/
public class JobRemovalService extends JSONServiceSpring
{

    private String uid;

    /** @param im The InterMine state object **/
    public JobRemovalService(InterMineAPI im, Format format, String uid) {
        super(im, format);
        this.uid = uid;
    }

    @Override
    protected void execute() throws Exception {
        IDResolver idresolver = IDResolver.getInstance();

        if (idresolver.removeJob(uid) == null) {
            throw new ResourceNotFoundException("Unknown id: " + uid);
        }
    }

}

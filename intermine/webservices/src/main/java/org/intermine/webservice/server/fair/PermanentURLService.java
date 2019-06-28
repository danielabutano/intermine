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

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.web.logic.PermanentURIHelper;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.PermanentUrl;
import org.intermine.webservice.server.core.JSONService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Generate a permanent URL given a type and internal InterMine ID
 * Permanent URLs are used in the Share button and to set the attribute 'url' in Schema.org
 *
 * The url returned will be empty in the following cases:
 * type is not defined in the model,
 * type is defined in the model but we are not able to generate a permanent URI
 * id is wrong
 *
 * @author Daniela Butano
 */
public class PermanentURLService extends JSONServiceSpring
{

    public PermanentUrl getPermanentUrl() {
        return permanentUrl;
    }

    private PermanentUrl permanentUrl;

    /**
     * The constructor
     * @param im the intermine api
     */
    public PermanentURLService(InterMineAPI im) {
        super(im);
        permanentUrl = new PermanentUrl();
    }

    @Override
    protected void execute() throws Exception {
        String type = getRequiredParameter("type");
        String id = getRequiredParameter("id");
        String url = (new PermanentURIHelper(request)).getPermanentURL(type,
                Integer.parseInt(id));
        if (url == null) {
            permanentUrl.setUrl(StringUtils.EMPTY);
        } else {
            permanentUrl.setUrl(url);
        }
    }

}

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

import java.util.Arrays;
import java.util.List;

import org.intermine.api.InterMineAPI;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.Schema;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Serve up the list of schemata that we have.
 * @author Alexis Kalderimis
 *
 */
public class SchemaListService extends JSONServiceSpring
{

    public Schema getSchema() {
        return schema;
    }

    private Schema schema;

    /**
     * Constructor
     * @param im InterMine settings
     * @param format
     */
    public SchemaListService(InterMineAPI im, Format format) {
        super(im, format);
        schema = new Schema();
    }

    /*
     * @see org.intermine.webservice.server.WebService#execute(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void execute() throws Exception {
        List<String> schemata =
            Arrays.asList(webProperties.getProperty("schema.filenames", "").split(","));
        schema.setSchemata(schemata);
    }

    @Override
    protected String getResultsKey() {
        return "schemata";
    }


}

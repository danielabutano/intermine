package org.intermine.web.logic;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * @author Daniela Butano
 **/
public final class WebServiceConstants
{
    private WebServiceConstants() {
        // Hidden constructor.
    }

    /**
     * Name of module of web services.
     * All web services relative urls start with following prefix.
     */
    public static final String MODULE_NAME = "service";

    /** Name of parameter with query **/
    public static final String QUERY_PARAMETER = "query";

    /** Layout parameter name. **/
    public static final String LAYOUT_PARAMETER = "layout";

    /**Name of format parameter that specifies format of returned results. */
    public static final String OUTPUT_PARAMETER = "format";

    /** Value of parameter when user wants tab separated output to be returned. **/
    public static final String FORMAT_PARAMETER_TAB = "tab";

    /** Value of parameter when user wants html output to be returned. **/
    public static final String FORMAT_PARAMETER_HTML = "html";
}

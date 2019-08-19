package org.intermine.webservice.server.widget;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.web.logic.export.ResponseUtil;
import org.intermine.web.logic.widget.config.WidgetConfig;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.Widgets;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.output.JSONFormatter;
import org.intermine.webservice.server.output.Output;
import org.intermine.webservice.server.output.StreamedOutput;
import org.intermine.webservice.server.output.XMLFormatter;

/**
 * A service for listing the available widgets.
 * @author Alex Kalderimis
 *
 */
public class AvailableWidgetsService extends JSONServiceSpring
{

    public Widgets getWidgetsModel() {
        return widgetsModel;
    }

    private Widgets widgetsModel;

    /**
     * Constructor
     * @param im The InterMine application object.
     */
    public AvailableWidgetsService(InterMineAPI im, Format format) {
        super(im, format);
        widgetsModel = new Widgets();
    }

    @Override
    protected void execute() throws Exception {
        WebConfig webConfig = InterMineContext.getWebConfig();
        Map<String, WidgetConfig> widgetDetails = webConfig.getWidgets();

        WidgetProcessor processor = getProcessor();
        Iterator<Entry<String, WidgetConfig>> it = widgetDetails.entrySet().iterator();
        List<Object> resultList = new ArrayList<>();
        while (it.hasNext()) {
            Entry<String, WidgetConfig> pair = it.next();
            resultList.add(processor.processSpring(pair.getKey(), pair.getValue()));
        }
        widgetsModel.setWidgets(resultList);
    }

    private WidgetProcessor getProcessor() {
        if (formatIsJSON()) {
            return JSONWidgetProcessor.instance();
        } else if (formatIsXML()) {
            return XMLWidgetProcessor.instance();
        } else {
            return FlatWidgetProcessor.instance();
        }
    }


}

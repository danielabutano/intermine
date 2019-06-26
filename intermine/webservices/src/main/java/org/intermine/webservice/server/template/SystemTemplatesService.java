package org.intermine.webservice.server.template;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.intermine.api.InterMineAPI;
import org.intermine.api.template.ApiTemplate;
import org.intermine.api.template.TemplateHelper;
import org.intermine.api.template.TemplateManager;
import org.intermine.pathquery.PathQuery;
import org.intermine.web.logic.export.ResponseUtil;
import org.intermine.webservice.WebServiceSpring;
import org.intermine.webservice.model.TemplatesSystem;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.WebService;
import org.intermine.webservice.server.exceptions.NotAcceptableException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.server.output.JSONFormatter;
import org.intermine.webservice.server.output.Output;
import org.intermine.webservice.server.output.PlainFormatter;
import org.intermine.webservice.server.output.StreamedOutput;
import org.intermine.webservice.util.ResponseUtilSpring;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * Fetch the names of public template queries tagged with "im:converter"
 * for use with the System Templates web service.
 * @author Julie Sullivan
 */
public class SystemTemplatesService extends WebServiceSpring
{
    public TemplatesSystem getTemplatesSystem() {
        return templatesSystem;
    }

    private TemplatesSystem templatesSystem;

    /**
     * Constructor.
     * @param im The InterMineAPI for this webservice
     */
    public SystemTemplatesService(InterMineAPI im) {
        super(im);
        templatesSystem = new TemplatesSystem();
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.XML;
    }

    @Override
    protected boolean canServe(Format format) {
        return Format.BASIC_FORMATS.contains(format);
    }

    @Override
    protected void execute() throws Exception {

        TemplateManager templateManager = im.getTemplateManager();
        Map<String, ApiTemplate> templates = templateManager.getSystemTemplates();

        switch (getFormat()) {
            case XML:
                templatesSystem.setTemplates(TemplateHelper.apiTemplateMapToXml(templates,
                        PathQuery.USERPROFILE_VERSION));
                break;
            case JSON:
                templatesSystem.setTemplates(
                        TemplateHelper.apiTemplateMapToJson(im, templates, null));
                break;
            case TEXT:
                Set<String> templateNames = new TreeSet<String>(templates.keySet());
                for (String templateName : templateNames) {
                    templatesSystem.setTemplates(templateName);
                }
            case HTML:
                throw new ServiceException("Not implemented: " + Format.HTML);
            default:
                throw new NotAcceptableException();
        }
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        templatesSystem.setExecutionTime(executionTime);


        if (status >= 400) {
            templatesSystem.setWasSuccessful(false);
            templatesSystem.setError(escapeJava(errorMessage));
        } else {
            templatesSystem.setWasSuccessful(true);
        }
        templatesSystem.setStatusCode(status);
    }
}

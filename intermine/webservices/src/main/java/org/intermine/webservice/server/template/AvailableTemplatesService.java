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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.template.ApiTemplate;
import org.intermine.api.template.TemplateHelper;
import org.intermine.api.template.TemplateManager;
import org.intermine.pathquery.PathQuery;
import org.intermine.web.logic.export.ResponseUtil;
import org.intermine.webservice.WebServiceSpring;
import org.intermine.webservice.model.Templates;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.WebService;
import org.intermine.webservice.server.exceptions.NotAcceptableException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.server.output.JSONFormatter;
import org.intermine.webservice.server.output.Output;
import org.intermine.webservice.server.output.PlainFormatter;
import org.intermine.webservice.server.output.StreamedOutput;

/**
 * Fetch the names of public template queries for use with the Templates web service.
 * @author Richard Smith
 */
public class AvailableTemplatesService extends WebServiceSpring
{

    public Templates getTemplatesResponse() {
        return templatesResponse;
    }

    private Templates templatesResponse;

    /**
     * Constructor.
     * @param im The InterMineAPI for this webservice
     */
    public AvailableTemplatesService(InterMineAPI im, Format format) {
        super(im, format);
        templatesResponse = new Templates();
    }

    @Override
    protected void execute() throws Exception {

        TemplateManager templateManager = im.getTemplateManager();
        Map<String, ApiTemplate> templates;
        Profile profile = null;
        boolean includeBroken = Boolean.parseBoolean(request.getParameter("includeBroken"));
        if (isAuthenticated()) {
            profile = getPermission().getProfile();
            templates = (includeBroken)
                            ? templateManager.getUserAndGlobalTemplates(profile)
                            : templateManager.getWorkingTemplates(profile);
        } else {
            templates = (includeBroken)
                            ? templateManager.getGlobalTemplates()
                            : templateManager.getWorkingTemplates();
        }

        switch (getFormat()) {
            case XML:
                templatesResponse.setTemplates(TemplateHelper.apiTemplateMapToXml(templates,
                        PathQuery.USERPROFILE_VERSION));
                break;
            case JSON:
                /*Map<String, Object> attributes = new HashMap<String, Object>();
                if (formatIsJSONP()) {
                    attributes.put(JSONFormatter.KEY_CALLBACK, getCallback());
                }
                templatesResponse.setTemplates(TemplateHelper.apiTemplateMapToJson(im, templates, profile));
                */
                templatesResponse.setTemplates(TemplateHelper.apiTemplateMapToJsonSpring(im, templates, profile));
                break;
            case TEXT:
                Set<String> templateNames = new TreeSet<String>(templates.keySet());
                /*for (String templateName : templateNames) {
                    output.addResultItem(Arrays.asList(templateName));
                }*/
                templatesResponse.setTemplates(templateNames);
            case HTML:
                throw new ServiceException("Not implemented: " + Format.HTML);
            default:
                throw new NotAcceptableException();
        }
    }
}

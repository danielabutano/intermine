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

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.template.TemplateManager;
import org.intermine.template.TemplateQuery;
import org.intermine.web.logic.export.ResponseUtil;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.SavedTemplate;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.BadRequestException;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.output.Output;
import org.intermine.webservice.server.output.PlainFormatter;
import org.intermine.webservice.server.output.StreamedOutput;

/**
 * Service that responds with a single template.
 * @author Alex Kalderimis
 */
public class SingleTemplateService extends JSONServiceSpring {

    private String name;

    public SavedTemplate getSavedTemplate() {
        return savedTemplate;
    }

    private SavedTemplate savedTemplate;

    /** @param im The InterMine state object **/
    public SingleTemplateService(InterMineAPI im, Format format, String name) {
        super(im, format);
        savedTemplate = new SavedTemplate();
        this.name = name;
    }

    @Override
    protected void execute() {
        if (StringUtils.isBlank(name)) {
            throw new BadRequestException("No name provided");
        }
        Profile p = getPermission().getProfile();
        TemplateManager tm = im.getTemplateManager();
        TemplateQuery t = tm.getUserOrGlobalTemplate(p, name);
        if (t == null) {
            throw new ResourceNotFoundException("No template found called " + name);
        }
        if (Format.JSON == getFormat()) {
            savedTemplate.setTemplate(t.toJSONSpring());
        } else {
            savedTemplate.setTemplate(t.toXml());
        }
    }

    @Override
    public String getResultsKey() {
        return "template";
    }

}

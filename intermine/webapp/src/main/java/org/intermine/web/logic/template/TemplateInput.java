package org.intermine.web.logic.template;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.List;
import java.util.Map;

/**
 * TemplateInput is parameter object representing parameters to load template
 * @author Daniela Butano
 **/
public class TemplateInput implements TemplateInputInterface
{
    private String name;
    private Map<String, List<ConstraintInput>> constraints;

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public void setConstraints(Map<String, List<ConstraintInput>> constraints) {
        this.constraints = constraints;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, List<ConstraintInput>> getConstraints() {
        return constraints;
    }
}

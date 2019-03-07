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
 * Interface for template input parameters
 *
 * @author  Daniela Butano
 **/
public interface TemplateInputInterface
{
    /**
     * Returns template name.
     * @return name
     */
    String getName();

    /**
     * Sets template name.
     * @param name name
     */
    void setName(String name);

    /**
     * Sets constraints.
     * @param constraints constraints
     */
    void setConstraints(Map<String, List<ConstraintInput>> constraints);
    /**
     * Returns constraints.
     * @return constraints
     */
    Map<String, List<ConstraintInput>> getConstraints();

}

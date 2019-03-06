package org.intermine.web.logic.template;

import java.util.List;
import java.util.Map;

public interface TemplateInputInterface {
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

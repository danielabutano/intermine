package org.intermine.bio.web.logic;

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

/**
 * A helper class for export
 *
 * @author Daniela Butano
 */
public final class BioExportHelper
{
    private BioExportHelper() {
    }
    /**
     * @param clazzes classes
     * @param cls searched class
     * @return index of class that is assignable to given class
     */
    public static int getClassIndex(List<Class<?>> clazzes, Class<?> cls) {
        for (int i = 0; i < clazzes.size(); i++) {
            if (cls.isAssignableFrom(clazzes.get(i))) {
                return i;
            }
        }
        return -1;
    }
}

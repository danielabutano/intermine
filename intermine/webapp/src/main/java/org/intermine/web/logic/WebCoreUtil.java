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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.FieldDescriptor;
import org.intermine.metadata.Model;
import org.intermine.metadata.StringUtil;
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.pathquery.PathQuery;
import org.intermine.util.PropertiesUtil;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.config.FieldConfig;
import org.intermine.web.logic.config.FieldConfigHelper;
import org.intermine.web.logic.config.Type;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.web.logic.results.WebState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Utility methods for the web package.
 *
 * @author Kim Rutherford
 * @author Julie Sullivan
 * @author Daniela BUtano
 */

public abstract class WebCoreUtil
{
    protected static final Logger LOG = Logger.getLogger(WebCoreUtil.class);

    protected WebCoreUtil() {
        // don't
    }

    /**
     * Formats a column name, using the webconfig to produce configured labels.
     * EG: MRNA.scoreType --&gt; mRNA &gt; Score Type
     *
     * @param viewColumn
     *            A path representing a column name
     * @param webConfig
     *            The configuration to find labels in
     * @return A formatted column name
     */
    public static String formatPath(final Path viewColumn, final WebConfig webConfig) {
        final ClassDescriptor cd = viewColumn.getStartClassDescriptor();
        if (viewColumn.isRootPath()) {
            return formatClass(cd, webConfig);
        } else {
            return formatClass(cd, webConfig) + " > " + formatFieldChain(viewColumn, webConfig);
        }
    }

    /**
     * Format a sequence of fields in a chain.
     * @param p The path representing the fields to format.
     * @param config The web-configuration.
     * @return A formatted string, without the root class.
     */
    public static String formatFieldChain(final Path p, final WebConfig config) {
        if (p == null) {
            return "";
        }
        final ClassDescriptor cd = p.getStartClassDescriptor();
        if (p.endIsAttribute()) {
            final Type type = config.getTypes().get(cd.getName());
            if (type != null) {
                final String pathString = p.getNoConstraintsString();
                final FieldConfig fcg = type.getFieldConfig(
                        pathString.substring(pathString.indexOf(".") + 1));
                if (fcg != null) {
                    return fcg.getDisplayName();
                }
            }
        }

        List<String> elems = p.getElements();
        String firstField = elems.get(0);
        if (firstField == null) {
            return ""; // shouldn't actually happen - but we shouldn't throw exceptions here.
        }
        FieldDescriptor fd = cd.getFieldDescriptorByName(firstField);
        final FieldConfig fc = FieldConfigHelper.getFieldConfig(config, cd, fd);
        String thisPart = "";
        if (fc != null) {
            thisPart = fc.getDisplayName();
        } else {
            thisPart = FieldConfig.getFormattedName(fd.getName());
        }
        if (elems.size() > 1) {
            String root = p.decomposePath().get(1).getLastClassDescriptor().getUnqualifiedName();
            String[] parts = p.toString().split("\\."); // use toString to get subclass info.
            int start = Math.min(2, parts.length - 1);
            String fields = StringUtils.join(Arrays.copyOfRange(parts, start, parts.length), ".");
            String nextPathString = root + "." + fields;
            Path newPath;
            try {
                newPath = new Path(p.getModel(), nextPathString);
            } catch (PathException e) {
                newPath = null;
            }
            return thisPart + " > " + formatFieldChain(newPath, config);
        } else {
            return thisPart;
        }
    }

    /**
     * Formats a class name, using the web-config to produce configured labels.
     * @param cd The class to display.
     * @param config The web-configuration.
     * @return A nicely labelled string.
     */
    public static String formatClass(ClassDescriptor cd, WebConfig config) {
        Type type = config.getTypes().get(cd.getName());
        if (type == null) {
            return Type.getFormattedClassName(cd.getUnqualifiedName());
        } else {
            return type.getDisplayName();
        }
    }

}

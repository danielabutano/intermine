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
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.pathquery.PathQuery;
import org.intermine.web.logic.config.FieldConfig;
import org.intermine.web.logic.config.FieldConfigHelper;
import org.intermine.web.logic.config.Type;
import org.intermine.web.logic.config.WebConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Utility methods for webapp and webservices modules.
 *
 * @author Kim Rutherford
 * @author Julie Sullivan
 */

public abstract class WebCoreUtil
{
    protected static final Logger LOG = Logger.getLogger(WebCoreUtil.class);

    /**
     * constructor
     */
    protected WebCoreUtil() {
        // don't
    }

    /**
     * Formats a column name, using the webconfig to produce configured labels.
     * EG: MRNA.scoreType --&gt; mRNA &gt; Score Type
     *
     * @param original
     *            The column name (a path string) to format
     * @param request
     *            The request to use to get the configuration off.
     * @return A formatted column name
     */
    public static String formatPath(final String original,
            final HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        ServletContext context = request.getSession().getServletContext();
        final InterMineAPI im = (InterMineAPI) context.getAttribute(Constants.INTERMINE_API);
        final Model model = im.getModel();
        final WebConfig webConfig = (WebConfig) context.getAttribute(Constants.WEBCONFIG);
        return formatPath(original, model, webConfig);
    }

    /**
     * Format a query's view into a list of displayable strings, taking both
     * the query's path descriptions and the application's web configuration into
     * account.
     * @param pq The query to format
     * @param wc The configuration to use to find labels in
     * @return A list of displayable strings
     */
    public static List<String> formatPathQueryView(final PathQuery pq, final WebConfig wc) {
        final List<String> formattedViews = new ArrayList<String>();
        for (final String view : pq.getView()) {
            formattedViews.add(formatPathDescription(view, pq, wc));
        }
        return formattedViews;
    }

    /**
     * Formats a column name, using the webconfig to produce configured labels.
     * EG: MRNA.scoreType --&gt; mRNA &gt; Score Type
     *
     * @param original The column name (a path string) to format
     * @param model The model to use to parse the string
     * @param webConfig The configuration to find labels in
     * @return A formatted column name
     */
    public static String formatPath(final String original, final Model model,
            final WebConfig webConfig) {
        Path viewPath;
        try {
            viewPath = new Path(model, original);
        } catch (final PathException e) {
            return original;
        }
        return formatPath(viewPath, webConfig);
    }

    /**
     * Formats a column name, using the given query to construct a path according to the current
     * state of its subclasses.
     * @param path The path to format.
     * @param pq The query to use for path construction.
     * @param config The configuration to find labels in.
     * @return A nicely formatted string.
     */
    public static String formatPath(final String path, final PathQuery pq, final WebConfig config) {
        Path viewPath;
        try {
            viewPath = pq.makePath(path);
        } catch (Throwable t) {
            // In all error cases, return the original string.
            return path;
        }
        return formatPath(viewPath, config);
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

    /**
     * Format a path into a displayable field name.
     *
     * eg: Employee.fullTime &rarr; Full Time
     *
     * @param p A path
     * @param webConfig The Web Configuration
     * @return A displayable string
     */
    public static String formatField(final Path p, final WebConfig webConfig) {
        if (p == null) {
            return "";
        }

        final FieldDescriptor fd = p.getEndFieldDescriptor();
        if (fd == null) {
            return "";
        }
        final ClassDescriptor cld = fd.isAttribute()
                ? p.getLastClassDescriptor() : p.getSecondLastClassDescriptor();

        final FieldConfig fc = FieldConfigHelper.getFieldConfig(webConfig, cld,
                fd);
        if (fc != null) {
            return fc.getDisplayName();
        } else {
            return FieldConfig.getFormattedName(fd.getName());
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

    private static String replaceDescribedPart(final String s,
            final Map<String, String> descriptions) {
        final String retval = descriptions.get(s);
        if (retval == null) {
            final int lastDot = s.lastIndexOf('.');
            if (lastDot == -1) {
                return s;
            } else {
                return replaceDescribedPart(s.substring(0, lastDot),
                        descriptions) + " > " + s.substring(lastDot + 1);
            }
        } else {
            return retval;
        }
    }

    /**
     * Return a string suitable for displaying a PathQuery's path, taking any
     * path descriptions it has configured into account.
     *
     * @param s The path to display
     * @param pq The PathQuery it relates to
     * @param config The Web-Configuration to use to lookup labels
     * @return A string suitable for external display.
     */
    public static String formatPathDescription(final String s,
            final PathQuery pq, final WebConfig config) {
        Path p;
        try {
            p = pq.makePath(s);
        } catch (final PathException e) {
            return formatPath(s, pq.getModel(), config); // Format it nicely
            // anyway
        }

        return formatPathDescription(p, pq, config);
    }

    /**
     * So we can test set membership in JSPs.
     * @param things The things
     * @param o The thing
     * @return Whether the thing is one of the things.
     */
    public static boolean contains(Collection<?> things, Object o) {
        return things.contains(o);
    }

    /**
     * So we can test map membership in JSPs.
     * @param mapping The things
     * @param o The thing
     * @return Whether the thing is one of the keys in things.
     */
    public static boolean containsKey(Map<?, ?> mapping, Object o) {
        return mapping.containsKey(o);
    }

    /**
     * Return a string suitable for displaying a PathQuery's path, taking any
     * path descriptions it has configured into account.
     *
     * @param p The path to display
     * @param pq The PathQuery it relates to
     * @param config The Web-Configuration to use to lookup labels
     * @return A string suitable for external display.
     */
    public static String formatPathDescription(final Path p, final PathQuery pq,
            final WebConfig config) {
        final Map<String, String> descriptions = pq.getDescriptions();
        final String withLabels = formatPath(p, config);
        final List<String> labeledParts = Arrays.asList(StringUtils
                .splitByWholeSeparator(withLabels, " > "));

        if (descriptions.isEmpty()) {
            return StringUtils.join(labeledParts, " > ");
        }
        final String withReplaceMents = replaceDescribedPart(
                p.getNoConstraintsString(), descriptions);
        final List<String> originalParts = Arrays.asList(
                StringUtils.split(p.getNoConstraintsString(),
                '.'));
        final int originalPartsSize = originalParts.size();
        final List<String> replacedParts = Arrays.asList(StringUtils
                .splitByWholeSeparator(withReplaceMents, " > "));
        final int replacedSize = replacedParts.size();

        // Else there are some described path segments...
        int partsToKeepFromOriginal = 0;
        int partsToTakeFromReplaced = replacedSize;
        for (int i = 0; i < originalPartsSize; i++) {
            final String fromOriginal = originalParts.get(originalPartsSize
                    - (i + 1));
            final int replaceMentsIndex = replacedSize - (i + 1);
            final String fromReplacement = replaceMentsIndex > 0 ? replacedParts
                    .get(replaceMentsIndex) : null;
            if (fromOriginal != null && fromOriginal.equals(fromReplacement)) {
                partsToKeepFromOriginal++;
                partsToTakeFromReplaced--;
            }
        }
        final List<String> returners = new ArrayList<String>();
        if (partsToTakeFromReplaced > 0) {
            returners.addAll(replacedParts.subList(0, partsToTakeFromReplaced));
        }
        if (partsToKeepFromOriginal > 0) {
            final int start
                = Math.max(0, labeledParts.size() - partsToKeepFromOriginal);
            final int end
                = start + partsToKeepFromOriginal - (originalPartsSize - labeledParts.size());
            returners.addAll(labeledParts.subList(start, end));
        }

        return StringUtils.join(returners, " > ");
    }
}

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.pathquery.PathQuery;
import org.intermine.util.PropertiesUtil;
import org.intermine.metadata.StringUtil;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.web.logic.results.WebState;
import org.intermine.web.logic.session.SessionMethods;

/**
 * Utility methods for the web package.
 *
 * @author Kim Rutherford
 * @author Julie Sullivan
 */

public abstract class WebUtil extends WebCoreUtil
{
    protected static final Logger LOG = Logger.getLogger(WebUtil.class);

    private WebUtil() {
        // don't
    }

    /**
     * Lookup an Integer property from the SessionContext and return it.
     *
     * @param session
     *            the current session
     * @param propertyName
     *            the property to find
     * @param defaultValue
     *            the value to return if the property isn't present
     * @return the int value of the property
     */
    public static int getIntSessionProperty(final HttpSession session,
            final String propertyName, final int defaultValue) {
        final Properties webProperties = SessionMethods
                .getWebProperties(session.getServletContext());
        final String n = webProperties.getProperty(propertyName);

        int intVal = defaultValue;

        try {
            intVal = Integer.parseInt(n);
        } catch (final NumberFormatException e) {
            LOG.warn("Failed to parse " + propertyName + " property: " + n);
        }

        return intVal;
    }

    /**
     * takes a map and puts it in random order also shortens the list to be
     * map.size() = max
     *
     * @param map
     *            The map to be randomised - the Map will be unchanged after the
     *            call
     * @param max
     *            the number of items to be in the final list
     * @param <V>
     *            the value type
     * @return the newly randomised, shortened map
     */
    public static <V> Map<String, V> shuffle(final Map<String, V> map,
            final int max) {
        List<String> keys = new ArrayList<String>(map.keySet());

        Collections.shuffle(keys);

        if (keys.size() > max) {
            keys = keys.subList(0, max);
        }

        final Map<String, V> returnMap = new HashMap<String, V>();

        for (final String key : keys) {
            returnMap.put(key, map.get(key));
        }
        return returnMap;
    }

    /**
     * Return the contents of the page given by prefixURLString + '/' + path as
     * a String. Any relative links in the page will be modified to go via
     * showStatic.do
     *
     * @param prefixURLString
     *            the prefix (including "http://...") of the web site to read
     *            from. eg. http://www.flymine.org/doc/help
     * @param path
     *            the page to retrieve eg. manualFlyMineHome.shtml
     * @return the contents of the page
     * @throws IOException
     *             if there is a problem while reading
     */
    public static String getStaticPage(final String prefixURLString,
            final String path) throws IOException {
        final StringBuffer buf = new StringBuffer();

        final URL url = new URL(prefixURLString + '/' + path);
        final URLConnection connection = url.openConnection();
        final InputStream is = connection.getInputStream();
        final Reader reader = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(reader);
        String line;
        while ((line = br.readLine()) != null) {
            // replace relative urls ie. href="manualExportfasta.shtml"
            line = line.replaceAll("href=\"([^\"]+)\"",
                    "href=\"showStatic.do?path=$1\"");
            buf.append(line + "\n");
        }
        return buf.toString();
    }

    /**
     * Look at the current webapp page and subtab and return the help page and
     * tab.
     *
     * @param request
     *            the request object
     * @return the help page and tab
     */
    public static String[] getHelpPage(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final ServletContext servletContext = session.getServletContext();
        final Properties webProps = SessionMethods
                .getWebProperties(servletContext);
        final WebState webState = SessionMethods.getWebState(request
                .getSession());
        final String pageName = (String) request.getAttribute("pageName");
        final String subTab = webState.getSubtab("subtab" + pageName);

        String prop;
        if (subTab == null) {
            prop = webProps.getProperty("help.page." + pageName);
        } else {
            prop = webProps.getProperty("help.page." + pageName + "." + subTab);
        }

        if (prop == null) {
            return new String[0];
        }
        return StringUtil.split(prop, ":");
    }

    /**
     * A bean encapsulating a resource (js or css).
     * @author Alex Kalderimis
     *
     */
    public static final class HeadResource
    {
        private final String type;
        private final String url;
        private final String key;

        private HeadResource(String key, String type, String url) {
            this.key = key;
            this.type = type;
            this.url = url;
        }

        /** @return the key **/
        public String getKey() {
            return key;
        }

        /** @return the type **/
        public String getType() {
            return type;
        }

        /** @return the url **/
        public String getUrl() {
            return url;
        }

        /** @return whether this resource is relative **/
        public boolean getIsRelative() {
            return url.startsWith("/");
        }

        @Override
        public String toString() {
            return String.format("HeadResouce [type = %s, url = %s]", type, url);
        }
    }

    /**
     * Returns the resources for a particular section of the head element.
     *
     * @param section The section this resource belongs in.
     * @param userPreferences The preferences of the current user.
     *
     * @return A list of page resources, which are the urls for these resources.
     */
    public static List<HeadResource> getHeadResources(String section,
                                           Map<String, String> userPreferences) {
        Properties webProperties = InterMineContext.getWebProperties();
        String cdnLocation = webProperties.getProperty("head.cdn.location");
        boolean allowUserOverrides =
            "true".equals(webProperties.getProperty("head.allow.user.overrides"));
        List<HeadResource> ret = new ArrayList<HeadResource>();
        for (String type: new String[] {"css", "js" }) {
            String key = String.format("head.%s.%s.", type, section);
            Properties userProps = new Properties();
            userProps.putAll(webProperties);
            if (allowUserOverrides && userPreferences != null) {
                userProps.putAll(userPreferences);
            }
            Properties matches = PropertiesUtil.getPropertiesStartingWith(key, userProps);
            Set<Object> keys = new TreeSet<Object>(matches.keySet());
            for (Object o: keys) {
                String propName = String.valueOf(o);
                String value = matches.getProperty(propName);
                if (StringUtils.isBlank(value)) {
                    LOG.warn("Head resource configured with blank value: skipping " + propName);
                } else {
                    if (value.startsWith("CDN")) {
                        value = value.replace("CDN", cdnLocation);
                    } else if (!(value.startsWith("/") || value.startsWith("http"))) {
                        value = String.format("/%s/%s", type, value);
                    }
                    HeadResource resource = new HeadResource(propName, type, value);
                    ret.add(resource);
                }
            }
        }
        return ret;
    }

    /**
     * Formats column name. Replaces " &gt; " with "&amp;nbsp;&amp;gt; ".
     *
     * @param original
     *            original column name
     * @return modified string
     */
    public static String formatColumnName(final String original) {
        // replaces all dots and colons but not dots with following space - they
        // are probably
        // part of name, e.g. 'D. melanogaster'
        return original.replaceAll("&", "&amp;")
                .replaceAll(" > ", "&nbsp;&gt; ").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    /**
     * Format a query's view into a list of displayable strings, taking both
     * the query's path descriptions and the application's web configuration into
     * account.
     * @param pq The query to format
     * @param request The request to use to look up configuration from
     * @return A list of displayable strings
     */
    public static List<String> formatPathQueryView(final PathQuery pq,
            final HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        final WebConfig webConfig = SessionMethods.getWebConfig(request);
        return formatPathQueryView(pq, webConfig);
    }

    /**
     * Formats a column name, using the webconfig to produce configured labels.
     * EG: MRNA.scoreType --&gt; mRNA &gt; Score Type
     *
     * @param pathString
     *            A string representing a path to format
     * @param api
     *            the webapp configuration to aquire a model from
     * @param webConfig
     *            The configuration to find labels in
     * @return A formatted column name
     */
    public static String formatPath(final String pathString,
                                    final InterMineAPI api, final WebConfig webConfig) {
        Path viewPath;
        try {
            viewPath = new Path(api.getModel(), pathString);
        } catch (Throwable t) {
            // In all error cases, return the original string.
            return pathString;
        }
        return formatPath(viewPath, webConfig);
    }

    /**
     * Format a path into a displayable field name.
     *
     * eg: Employee.fullTime &rarr; Full Time
     *
     * @param s A path represented as a string
     * @param api The InterMine settings bundle
     * @param webConfig The Web Configuration
     * @return A displayable string
     */
    public static String formatField(final String s, final InterMineAPI api,
            final WebConfig webConfig) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        Path viewPath;
        try {
            viewPath = new Path(api.getModel(), s);
        } catch (final PathException e) {
            return s;
        }
        return formatField(viewPath, webConfig);
    }

    /**
     * Format a path represented as a string to the formatted fields, without
     * the class name.
     *
     * So <code>Employee.department.manager.age<code> becomes
     * <code>Department &gt; Manager &gt; Years Alive</code>
     *
     * @param s The path string
     * @param api The InterMine API to use for model lookup.
     * @param webConfig The class name configuration.
     * @return A nicely formatted string.
     */
    public static String formatFieldChain(final String s,
            final InterMineAPI api, final WebConfig webConfig) {
        final String fullPath = formatPath(s, api.getModel(), webConfig);
        if (StringUtils.isEmpty(fullPath)) {
            return fullPath;
        } else {
            final int idx = fullPath.indexOf(">");
            if (idx != -1) {
                return fullPath.substring(idx + 1);
            }
        }
        return fullPath;
    }

    /**
     * Check whether the runtime type of an object supports a particular path.
     * E.g.: An Employee supports the "department.name" path.
     * @param obj The object we want to inspect.
     * @param path The headless path we want to use.
     * @param api A reference to the API object.
     * @return true if the path is valid for this object.
     */
    public static boolean hasValidPath(Object obj, String path, InterMineAPI api) {
        try {
            Model m = api.getModel();
            Collection<ClassDescriptor> clds = m.getClassDescriptorsForClass(obj.getClass());
            for (ClassDescriptor cd: clds) {
                new Path(api.getModel(), cd.getUnqualifiedName() + "." + path);
                return true;
            }
        } catch (PathException e) {
            // Ignore. Dynamic objects can be screwy.
        }
        return false;
    }
}

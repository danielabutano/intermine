package org.intermine.webservice.server.search;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.FacetField;
import org.intermine.api.InterMineAPI;
import org.intermine.api.bag.BagManager;
import org.intermine.api.searchengine.KeywordSearchFacetData;
import org.intermine.api.searchengine.KeywordSearchHandler;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.api.searchengine.KeywordSearchFacet;
import org.intermine.api.searchengine.KeywordSearchPropertiesManager;
import org.intermine.api.searchengine.KeywordSearchResults;
import org.intermine.api.searchengine.solr.SolrKeywordSearchHandler;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.RequestUtil;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.web.logic.export.Exporter;
import org.intermine.web.search.KeywordSearchResult;
import org.intermine.web.search.SearchUtils;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.QuickSearch;
import org.intermine.webservice.server.exceptions.BadRequestException;
import org.intermine.webservice.server.output.XMLFormatter;
import org.intermine.webservice.util.ResponseUtilSpring;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;


/**
 * A service that runs key-word searches.
 * @author Alex Kalderimis
 *
 */
public class QuickSearchService extends JSONServiceSpring
{
    private static final String FACET_PREFIX = "facet";
    private static final int PREFIX_LEN = FACET_PREFIX.length();

    private static final Logger LOG = Logger.getLogger(QuickSearch.class);

    private final ServletContext servletContext;


    public QuickSearch getQuickSearch() {
        return quickSearch;
    }

    private QuickSearch quickSearch;

    /**
     * @param im The InterMine state object
     * @param ctx The servlet context so that the index can be located.
     */
    public QuickSearchService(InterMineAPI im, ServletContext ctx) {
        super(im);
        this.servletContext = ctx;
        quickSearch = new QuickSearch();
    }

    @Override
    protected void execute() throws Exception {
        setHeadersPostInit();
        String contextPath = servletContext.getRealPath("/");

        KeywordSearchPropertiesManager keywordSearchPropertiesManager
                = KeywordSearchPropertiesManager.getInstance(im.getObjectStore());
        WebConfig wc = InterMineContext.getWebConfig();

        QuickSearchRequest input = new QuickSearchRequest();
        Vector<KeywordSearchFacetData> facets = keywordSearchPropertiesManager.getFacets();
        Map<String, String> facetValues = getFacetValues(facets);

        KeywordSearchHandler keywordSearchHandler = new SolrKeywordSearchHandler();

        KeywordSearchResults results = keywordSearchHandler
                .doKeywordSearch(im, input.searchTerm, facetValues,
                        input.getListIds(), input.offset);

        Collection<KeywordSearchResult> searchResultsParsed =
                SearchUtils.parseResults(im, wc, results.getHits());

        if (input.getIncludeFacets()) {
            Map<String, Object> facetData = new HashMap<String, Object>();
            for (KeywordSearchFacet kwsf: results.getFacets()) {
                Map<String, Integer> sfData = new HashMap<String, Integer>();

                List<FacetField.Count> items = kwsf.getItems();

                for ( FacetField.Count key : items) {
                    sfData.put(key.getName(), (int) key.getCount());
                }

                facetData.put(kwsf.getField(), sfData);
            }
            quickSearch.setFacets(facetData);
        }

        quickSearch.setTotalHits(String.valueOf(results.getTotalHits()));

        if (formatIsJSON()) {
            List< Map<String,Object> > result = new ArrayList<>();
            QuickSearchJSONProcessor processor = new QuickSearchJSONProcessor();
            Iterator<KeywordSearchResult> it = searchResultsParsed.iterator();
            for (int i = 0; input.wantsMore(i) && it.hasNext(); i++) {
                KeywordSearchResult kwsr = it.next();
                result.add(processor.formatResult(kwsr));
            }
            quickSearch.setResults(result);
        }
        else {
            List< List<String> > result = new ArrayList<>();
            QuickSearchResultProcessor processor = getProcessor();
            Iterator<KeywordSearchResult> it = searchResultsParsed.iterator();
            for (int i = 0; input.wantsMore(i) && it.hasNext(); i++) {
                KeywordSearchResult kwsr = it.next();
                result.add(processor.formatResult(kwsr,
                        input.wantsMore(i + 1) && it.hasNext()));
            }
            quickSearch.setResults(result);
        }
    }

    private Map<String, String> getFacetValues(Vector<KeywordSearchFacetData> facets) {
        HashMap<String, String> facetValues = new HashMap<String, String>();
    PARAM_LOOP:
        for (Enumeration<String> params = request.getParameterNames();
                params.hasMoreElements();) {
            String param = params.nextElement();
            String value = request.getParameter(param);
            if (!param.startsWith(FACET_PREFIX) || StringUtils.isBlank(value)) {
                continue;
            }
            String facetField = param.substring(PREFIX_LEN);
            if (StringUtils.isBlank(facetField)) {
                continue;
            }
            for (KeywordSearchFacetData facet: facets) {
                if (facetField.equals(facet.getField())) {
                    facetValues.put(facetField, value);
                    continue PARAM_LOOP;
                }
            }
        }
        return facetValues;
    }

    private class QuickSearchRequest
    {

        private final String searchTerm;
        private final int offset;
        private final Integer limit;
        private final String searchBag;
        private final boolean includeFacets;

        QuickSearchRequest() {

            String query = request.getParameter("q");
            if (StringUtils.isBlank(query)) {
                searchTerm = "*:*";
            } else {
                searchTerm = query;
            }
            LOG.debug(String.format("SEARCH TERM: '%s'", searchTerm));

            includeFacets = !Boolean.valueOf(request.getParameter("nofacets"));

            String limitParam = request.getParameter("size");
            Integer lim = null;
            if (!StringUtils.isBlank(limitParam)) {
                try {
                    lim = Integer.valueOf(limitParam);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Expected a number for size: got " + limitParam);
                }
            }
            this.limit = lim;

            String offsetP = request.getParameter("start");
            int parsed = 0;
            if (!StringUtils.isBlank(offsetP)) {
                try {
                    parsed = Integer.valueOf(offsetP);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Expected a number for start: got " + offsetP);
                }
            }
            offset = parsed;

            searchBag = request.getParameter("list");
        }

        public boolean wantsMore(int i) {
            if (limit == null) {
                return true;
            }
            return i < limit;
        }

        public boolean getIncludeFacets() {
            return includeFacets;
        }

        public String toString() {
            return String.format("<%s searchTerm=%s offset=%d>",
                    getClass().getName(), searchTerm, offset);
        }

        public List<Integer> getListIds() {
            List<Integer> ids = new ArrayList<Integer>();
            if (!StringUtils.isBlank(searchBag)) {
                LOG.debug("SEARCH BAG: '" + searchBag + "'");
                final BagManager bm = im.getBagManager();
                final Profile p = getPermission().getProfile();
                final InterMineBag bag = bm.getBag(p, searchBag);
                if (bag == null) {
                    throw new BadRequestException(
                            "You do not have access to a bag named '" + searchBag + "'");
                }
                ids.addAll(bag.getContentsAsIds());
            }
            return ids;
        }
    }

    private class QuickSearchXMLFormatter extends XMLFormatter
    {
        @Override
        public String formatResult(List<String> resultRow) {
            return StringUtils.join(resultRow, "");
        }
    }

    private QuickSearchResultProcessor getProcessor() {
        if (formatIsXML()) {
            return new QuickSearchXMLProcessor();
        } else {
            final String separator;
            if (RequestUtil.isWindowsClient(request)) {
                separator = Exporter.WINDOWS_SEPARATOR;
            } else {
                separator = Exporter.UNIX_SEPARATOR;
            }
            return new QuickSearchTextProcessor(separator);
        }
    }

    @Override
    protected void makeXMLOutput() {
        ResponseUtilSpring.setXMLHeader(responseHeaders, "search.xml");
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        quickSearch.setExecutionTime(executionTime);


        if (status >= 400) {
            quickSearch.setWasSuccessful(false);
            quickSearch.setError(escapeJava(errorMessage));
        } else {
            quickSearch.setWasSuccessful(true);
        }
        quickSearch.setStatusCode(status);
    }


}

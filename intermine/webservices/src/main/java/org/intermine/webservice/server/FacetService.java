package org.intermine.webservice.server;

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
import org.apache.solr.client.solrj.response.FacetField;
import org.intermine.api.InterMineAPI;
import org.intermine.api.searchengine.KeywordSearchFacet;
import org.intermine.api.searchengine.KeywordSearchFacetData;
import org.intermine.api.searchengine.KeywordSearchHandler;
import org.intermine.api.searchengine.KeywordSearchPropertiesManager;
import org.intermine.api.searchengine.solr.SolrKeywordSearchHandler;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.FacetSearch;
import org.intermine.webservice.server.core.JSONService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/**
 * A web service for returning facet related to particular query
 * @author arunans23
 *
 */
public class FacetService extends JSONServiceSpring
{

    private static final Logger LOG = Logger.getLogger(FacetService.class);

    public FacetSearch getFacetSearch() {
        return facetSearch;
    }

    private FacetSearch facetSearch;

    /**
     * Constructor
     * @param im InterMine settings
     */
    public FacetService(InterMineAPI im) {
        super(im);
        facetSearch = new FacetSearch();
    }

    @Override
    protected String getResultsKey() {
        return "facets";
    }

    @Override
    protected void execute() throws Exception {

        KeywordSearchPropertiesManager keywordSearchPropertiesManager
                = KeywordSearchPropertiesManager.getInstance(im.getObjectStore());

        Vector<KeywordSearchFacetData> facets = keywordSearchPropertiesManager.getFacets();

        KeywordSearchHandler searchHandler = new SolrKeywordSearchHandler();

        String searchTerm = request.getParameter("q");

        Map<String, String> facetValues = getFacetValues(request, facets);

        Collection<KeywordSearchFacet> keywordSearchFacets
                = searchHandler.doFacetSearch(im, searchTerm, facetValues);

        setHeadersPostInit();

        ArrayList< Map<String, Object>> rootArray = new ArrayList<>();

        for (KeywordSearchFacet<FacetField.Count> keywordSearchFacet : keywordSearchFacets) {

            ArrayList< Map<String, Object>> innerArray = new ArrayList<>();
            for (FacetField.Count count : keywordSearchFacet.getItems()) {
                Map<String, Object> innerObject = new HashMap<>();
                innerObject.put("name", count.getName());
                innerObject.put("value", count.getCount());
                innerArray.add(innerObject);
            }

            Map<String, Object> outerObject = new HashMap<>();
            outerObject.put(keywordSearchFacet.getName(), innerArray);
            rootArray.add(outerObject);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("results", rootArray);
        facetSearch.setFacets(result);

    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getFacetValues(HttpServletRequest request,
                                               Vector<KeywordSearchFacetData> facets) {
        HashMap<String, String> facetValues = new HashMap<String, String>();
        // if this is a new search (searchSubmit set) only keep facets if
        // searchSubmitRestricted used
        if (StringUtils.isBlank(request.getParameter("searchSubmit"))
                || !StringUtils.isBlank(request.getParameter("searchSubmitRestricted"))) {
            // find all parameters that begin with facet_ and have a
            // value, add them to map
            for (Map.Entry<String, String[]> requestParameter
                    : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
                if (requestParameter.getKey().startsWith("facet")
                        && requestParameter.getValue().length > 0
                        && !StringUtils.isBlank(requestParameter.getValue()[0])) {
                    String facetField = requestParameter.getKey().substring("facet".length());
                    boolean found = false;
                    for (KeywordSearchFacetData keywordSearchFacetData : facets) {
                        if (facetField.equals(keywordSearchFacetData.getField())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        facetValues.put(facetField, requestParameter.getValue()[0]);
                    }
                }
            }

            for (Map.Entry<String, String> facetValue : facetValues.entrySet()) {
                LOG.debug("SEARCH FACET: " + facetValue.getKey() + " = " + facetValue.getValue());
            }
        }
        return facetValues;
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        facetSearch.setExecutionTime(executionTime);


        if (status >= 400) {
            facetSearch.setWasSuccessful(false);
            facetSearch.setError(escapeJava(errorMessage));
        } else {
            facetSearch.setWasSuccessful(true);
        }
        facetSearch.setStatusCode(status);
    }
}

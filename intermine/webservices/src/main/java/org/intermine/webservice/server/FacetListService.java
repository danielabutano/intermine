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

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.FacetField;
import org.intermine.api.InterMineAPI;
import org.intermine.api.searchengine.KeywordSearchFacet;
import org.intermine.api.searchengine.KeywordSearchHandler;
import org.intermine.api.searchengine.solr.SolrKeywordSearchHandler;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.FacetList;
import org.intermine.webservice.server.core.JSONService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;


/**
 * A web service for returning all the facet list
 * @author arunans23
 *
 */
public class FacetListService extends JSONServiceSpring
{

    private static final Logger LOG = Logger.getLogger(FacetService.class);

    public FacetList getFacetList() {
        return facetList;
    }

    private FacetList facetList;

    /**
     * Constructor
     * @param im InterMine settings
     */
    public FacetListService(InterMineAPI im) {
        super(im);
        facetList = new FacetList();
    }

    @Override
    protected void execute() throws Exception {

        KeywordSearchHandler searchHandler = new SolrKeywordSearchHandler();

        //empty Map to pass to the method
        Map<String, String> facetValues = new HashMap<String, String>();

        Collection<KeywordSearchFacet> keywordSearchFacets
                = searchHandler.doFacetSearch(im, "*:*", facetValues);

        Map<String, List<String>> ckData = new HashMap<String, List<String>>();

        for (KeywordSearchFacet<FacetField.Count> keywordSearchFacet : keywordSearchFacets) {
            List<String> facetInnerList = new ArrayList<String>();

            for (FacetField.Count count : keywordSearchFacet.getItems()) {
                facetInnerList.add(count.getName());
            }

            ckData.put(keywordSearchFacet.getName(), facetInnerList);
        }

        facetList.setFacetlist(ckData);
    }

    @Override
    protected String getResultsKey() {
        return "facet-list";
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        facetList.setExecutionTime(executionTime);


        if (status >= 400) {
            facetList.setWasSuccessful(false);
            facetList.setError(escapeJava(errorMessage));
        } else {
            facetList.setWasSuccessful(true);
        }
        facetList.setStatusCode(status);
    }

}

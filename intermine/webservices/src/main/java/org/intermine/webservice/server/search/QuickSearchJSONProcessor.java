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

import java.util.HashMap;
import java.util.Map;

import org.intermine.web.search.KeywordSearchResult;
import org.json.JSONObject;

/**
 * A class that renders quick search results as JSON.
 * @author Alex Kalderimis
 *
 */
public class QuickSearchJSONProcessor
{
    public Map<String, Object> formatResult(KeywordSearchResult result) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", result.getType());
        data.put("id", result.getId());
        data.put("relevance", result.getScore());
        data.put("fields", result.getFieldValues());
        return data;
    }

}

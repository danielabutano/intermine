package org.intermine.webservice.server.query;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.api.InterMineAPI;
import org.intermine.api.query.BadQueryException;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.QueryStore;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.BadRequestException;

/**
 * A service that stores a query and associates it with an id.
 * @author Alex Kalderimis
 *
 */
public class QueryStoreService extends JSONServiceSpring
{

    public QueryStore getQueryStoreModel() {
        return queryStoreModel;
    }

    private QueryStore queryStoreModel;

    /** @param im The InterMine state object **/
    public QueryStoreService(InterMineAPI im, Format format) {
        super(im, format);
        queryStoreModel = new QueryStore();
    }

    @Override
    protected void execute() throws Exception {
        String xml = getRequiredParameter("query");
        String id;
        try {
            id = im.getQueryStore().putQuery(xml);
        } catch (BadQueryException e) {
            throw new BadRequestException(e.getMessage());
        }
        queryStoreModel.setId(id);
    }

}

package org.intermine.webservice.server.lists;

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
import org.intermine.api.bag.operations.SymmetricDifference;
import org.intermine.webservice.server.Format;

/**
 * A service for performing a symmetric difference operation on a collection
 * of lists.
 * @author Alex Kalderimis
 *
 */
public class ListDifferenceService extends ListOperationService
{

    /**
     * Constructor
     * @param im The InterMine application object.
     */
    public ListDifferenceService(InterMineAPI im, Format format) {
        super(im, format);
    }

    @Override
    protected SymmetricDifference getOperation(ListInput input) {
        return new SymmetricDifference(
                im.getModel(), getPermission().getProfile(), input.getLists());
    }

}

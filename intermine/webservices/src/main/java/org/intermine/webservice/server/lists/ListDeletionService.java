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
import org.intermine.api.profile.Profile;
import org.intermine.webservice.model.ListsDelete;
import org.intermine.webservice.server.Format;

import java.text.Normalizer;

/**
 * A service for deleting lists from the user-profile database.
 * @author Alex Kalderimis
 *
 */
public class ListDeletionService extends AuthenticatedListService
{

    public ListsDelete getListsDelete() {
        return listsDelete;
    }

    private ListsDelete listsDelete;

    /**
     * Constructor.
     * @param im The InterMine application object.
     */
    public ListDeletionService(InterMineAPI im, Format format) {
        super(im, format);
        listsDelete = new ListsDelete();
    }

    @Override
    protected void execute() throws Exception {
        Profile profile = getPermission().getProfile();
        ListInput input = getInput();
        listsDelete.setListName(input.getListName());
        ListServiceUtils.ensureBagIsDeleted(profile, input.getListName());
    }
}

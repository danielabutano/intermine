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
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.webservice.model.ListRename;
import org.intermine.webservice.server.Format;

/**
 * A service for renaming lists.
 * @author Alex Kalderimis
 *
 */
public class ListRenameService extends AuthenticatedListService
{

    public ListRename getListRename() {
        return listRename;
    }

    private ListRename listRename;

    /**
     * Constructor.
     * @param im The InterMine API settings.
     */
    public ListRenameService(InterMineAPI im, Format format) {
        super(im, format);
        listRename = new ListRename();
    }

    @Override
    protected void execute() throws Exception {
        Profile profile = getPermission().getProfile();

        ListRenameInput input = new ListRenameInput(request, bagManager, profile);

        profile.renameBag(input.getOldName(), input.getNewName());
        InterMineBag list = profile.getSavedBags().get(input.getNewName());

        listRename.setListName(list.getName());
        listRename.setListSize(list.size());
        listRename.setListId(list.getSavedBagId());
    }
}

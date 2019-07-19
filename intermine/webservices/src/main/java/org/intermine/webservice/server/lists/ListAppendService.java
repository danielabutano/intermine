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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.webservice.model.ListAppend;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.exceptions.ServiceForbiddenException;

/** @author Alex Kalderimis **/
public class ListAppendService extends ListUploadService
{

    public ListAppend getListAppend() {
        return listAppend;
    }

    private ListAppend listAppend;

    /** @param im The InterMine state object **/
    public ListAppendService(InterMineAPI im, Format format, String body) {
        super(im, format, body);
        listAppend = new ListAppend();
    }


    @Override
    protected void execute() throws Exception {
        final Profile profile = getPermission().getProfile();
        final ListInput input = getInput();

        listAppend.setListName(input.getListName());

        final String type = getNewListType(input);

        final Set<String> rubbishbin = new HashSet<String>();
        initialiseDelendumAccumulator(rubbishbin, input);
        try {
            makeList(input, type, profile, rubbishbin);
        } finally {
            for (final String delendum: rubbishbin) {
                ListServiceUtils.ensureBagIsDeleted(profile, delendum);
            }
        }
    }



    @Override
    protected void makeList(
            ListInput listInput,
            String type,
            Profile profile,
            Set<String> rubbishbin) throws Exception {

        Set<String> ids = new LinkedHashSet<String>();
        Set<String> unmatchedIds = new HashSet<String>();

        ListCreationInput input = (ListCreationInput) listInput;

        InterMineBag bag = profile.getSavedBags().get(input.getListName());
        if (bag == null) {
            throw new ServiceForbiddenException(
                input.getListName() + " is not a list you have access to");
        }

        processIdentifiers(bag.getType(), input, ids, unmatchedIds, bag);

        setListSize(bag.size());
        setListId(bag.getSavedBagId());

        listAppend.setUnmatchedIdentifiers(new ArrayList<>(unmatchedIds));
        /*for (Iterator<String> i = unmatchedIds.iterator(); i.hasNext();) {
            List<String> row = new ArrayList<String>(Arrays.asList(i.next()));
            if (i.hasNext()) {
                row.add("");
            }
            output.addResultItem(row);
        }*/
    }
}

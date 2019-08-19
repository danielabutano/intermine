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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.intermine.api.profile.InterMineBag;

/**
 * Formats a list into its name.
 * @author Alex Kalderimis
 *
 */
public class FlatListFormatter implements ListFormatter
{

    @Override
    public List<String> format(InterMineBag list) {
        return Arrays.asList(list.getName());
    }

    @Override
    public Map<String, Object> formatSpring(InterMineBag list) {
        return null;
    }

    @Override
    public void setSize(int size) {
        // No-op implementation.
    }

}

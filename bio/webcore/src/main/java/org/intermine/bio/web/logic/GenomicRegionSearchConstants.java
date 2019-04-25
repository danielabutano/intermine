package org.intermine.bio.web.logic;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * A class to provide genomic region search constants.
 *
 * @author Daniela Butano
 */
public final class GenomicRegionSearchConstants
{
    private GenomicRegionSearchConstants() {
        // don't instantiate
    }
    /**
     * Default batch size to be used for region search initialisation queries.
     */
    public static final int DEFAULT_REGION_INIT_BATCH_SIZE = 10000;
}

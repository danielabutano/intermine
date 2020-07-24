package org.intermine.webservice.server.output;

/*
 * Copyright (C) 2002-2020 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.PrintWriter;
import java.util.List;

public class RDFOutput extends Output {
    private PrintWriter writer;
    private int resultsCount;

    public RDFOutput(PrintWriter writer) {
        this.writer = writer;
    }

    /** Forwards data to associated writer
     * @param item data
     * **/
    @Override
    public void addResultItem(List<String> item) {
        //generate ntriples
        RDF rdf = new SimpleRD();
        resultsCount++;
    }

    @Override
    public void flush() {
        writer.flush();
        writer.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResultsCount() {
        return resultsCount;
    }
}

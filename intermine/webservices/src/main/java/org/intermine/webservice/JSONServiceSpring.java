package org.intermine.webservice;

import org.intermine.api.InterMineAPI;
import org.intermine.api.bag.BagManager;
import org.intermine.metadata.Model;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.output.JSONFormatter;

import java.util.HashMap;
import java.util.Map;

public class JSONServiceSpring extends WebServiceSpring {

    protected final BagManager bagManager;
    protected final Model model;

    private final Map<String, String> kvPairs = new HashMap<String, String>();

    /**
     * Constructor
     * @param im The InterMine configuration object.
     */
    public JSONServiceSpring(InterMineAPI im) {
        super(im);
        bagManager = im.getBagManager();
        model = im.getObjectStore().getModel();
    }

    /**
     * @return The key for the results property.
     */
    protected String getResultsKey() {
        return null;
    }

    /**
     * @return Whether to treat this as a lazy list.
     */
    protected boolean lazyList() {
        return false;
    }

    @Override
    protected Format getDefaultFormat() {
        return Format.JSON;
    }
}

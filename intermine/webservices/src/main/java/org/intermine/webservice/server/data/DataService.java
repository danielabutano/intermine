package org.intermine.webservice.server.data;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.query.MainHelper;
import org.intermine.metadata.AttributeDescriptor;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.model.FastPathObject;
import org.intermine.objectstore.ObjectStore;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.objectstore.query.Query;
import org.intermine.objectstore.query.SingletonResults;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.PathQuery;
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.JBrowseData;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.BadRequestException;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

/** @author Alex Kalderimis **/
public class DataService extends JSONServiceSpring
{

    public JBrowseData getjBrowseData() {
        return jBrowseData;
    }

    private JBrowseData jBrowseData;

    private String pathInfo;

    /** @param im The InterMine state object **/
    public DataService(InterMineAPI im, String type) {
        super(im);
        jBrowseData = new JBrowseData();
        pathInfo = type;
    }

    @Override
    protected String getResultsKey() {
        return "results";
    }

    @Override
    protected boolean lazyList() {
        return true;
    }

    @Override
    protected void execute() {

        String rangeHeader = request.getHeader("Range");
        int start = -1, end = -1;
        if (StringUtils.isNotBlank(rangeHeader)) {
            String[] parts = rangeHeader.replace("records=", "").split("-");
            start = (StringUtils.isBlank(parts[0]) ? 0 : Integer.parseInt(parts[0], 10));
            end = (StringUtils.isBlank(parts[1]) ? -1 : Integer.parseInt(parts[1], 10));
        }
        if (StringUtils.isBlank(pathInfo)) {
            throw new ResourceNotFoundException(pathInfo + " not found.");
        }
        String className = pathInfo;
        Model m = im.getModel();
        ClassDescriptor cd = m.getClassDescriptorByName(className);
        if (cd == null) {
            throw new ResourceNotFoundException(className + " not found.");
        }
        PathQuery pq = new PathQuery(m);
        pq.addView(className + ".id");
        @SuppressWarnings("unchecked")
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String value = request.getParameter(param);
            String pathString = String.format("%s.%s", className, param);
            Path p;
            try {
                p = new Path(m, pathString);
            } catch (PathException e) {
                throw new BadRequestException(pathString + " is not a valid relationship.");
            }
            if (p.endIsAttribute()) {
                pq.addConstraint(Constraints.equalsExactly(pathString, value));
            } else {
                pq.addConstraint(Constraints.lookup(pathString, value, ""));
            }
        }
        Profile p = getPermission().getProfile();
        Query q;
        try {
            q = MainHelper.makeQuery(
                    pq,
                    p.getCurrentSavedBags(),
                    null,
                    im.getBagQueryRunner(),
                    null);
        } catch (ObjectStoreException e) {
            throw new ServiceException("Could not make query.", e);
        }

        ObjectStore os = im.getObjectStore();
        SingletonResults results = os.executeSingleton(q);

        if (results.size() == 0) {
            throw new ResourceNotFoundException("No " + className + "s found.");
        }

        Iterator<Object> iter;
        if (start >= 0) {
            try {
                if (end < 0 || end >= results.size()) {
                    end = results.size() - 1;
                }
                if (start >= results.size()) {
                    ServiceException e = new ServiceException(
                            "Range not satisfiable: Start exceeds size.", 416);
                    throw e;
                }
                if (end < start) {
                    ServiceException e = new ServiceException(
                            "Range not satisfiable: end is less than start.", 416);
                    throw e;
                }
                iter = results.range(start, end).iterator();
                httpStatus = HttpStatus.valueOf(206);
                //response.setStatus(206); // Partial content.
            } catch (ObjectStoreException e) {
                throw new ServiceException("Could not retrieve results.", e);
            }
        } else {
            iter = results.iterator();
        }
        Set<AttributeDescriptor> attrs = cd.getAllAttributeDescriptors();
        List< Map<String, Object> > resultList = new ArrayList<>();
        while (iter.hasNext()) {
            FastPathObject result = (FastPathObject) iter.next();
            Map<String, Object> item = new HashMap<String, Object>();
            for (AttributeDescriptor ad: attrs) {
                String field = ad.getName();
                try {
                    item.put(field, result.getFieldValue(field));
                } catch (IllegalAccessException e) {
                    throw new ServiceException("Could not read " + field, e);
                }
            }
            resultList.add(item);
            //addResultItem(item, iter.hasNext());
        }
        jBrowseData.setResults(resultList);
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        jBrowseData.setExecutionTime(executionTime);


        if (status >= 400) {
            jBrowseData.setWasSuccessful(false);
            jBrowseData.setError(escapeJava(errorMessage));
        } else {
            jBrowseData.setWasSuccessful(true);
        }
        jBrowseData.setStatusCode(status);
    }

}

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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.BuildException;
import org.intermine.bio.web.model.GenomicRegion;
import org.intermine.objectstore.query.Query;
import org.intermine.web.logic.session.SessionMethods;

/**
 * This utility class instance a GenomicRegionSearchService object based on mine's setting.
 *
 * @author Fengyuan Hu
 */
public abstract class GenomicRegionSearchUtil extends GenomicRegionSearchCoreUtil
{
    private GenomicRegionSearchUtil() {
    }

    /**
     * Generate GenomicRegionSearchService object by using Java reflection
     *
     * @param request HttpServletRequest
     * @return the current mine's GenomicRegionSearchService object
     */
    public static GenomicRegionSearchService getGenomicRegionSearchService(HttpServletRequest
        request) {

        // Get service class name from web.properties
        String serviceClassName = (String) SessionMethods.getWebProperties(
                request.getSession().getServletContext()).get("genomicRegionSearch.service");

        GenomicRegionSearchService grsService = null;
        if (serviceClassName == null || "".equals(serviceClassName)) {
            grsService = new GenomicRegionSearchService();
            grsService.init(request);
        } else { // reflection
            Class<?> serviceClass;
            try {
                serviceClass = Class.forName(serviceClassName);
            } catch (ClassNotFoundException e) {
                throw new BuildException("Class not found for " + serviceClassName, e);
            }
            Class<?> [] types = new Class[] {HttpServletRequest.class};
            Object [] args = new Object[] {request};
            try {
                grsService = (GenomicRegionSearchService) serviceClass
                    .getConstructor(types).newInstance(args);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return grsService;
    }

    /**
     * Create a list of queries from user regions.
     *
     * @param genomicRegions list of gr
     * @param extension the flanking
     * @param organismName org short name
     * @param featureTypes ft
     * @param strandSpecific flag
     * @return map of gr-query
     */
    public static Map<GenomicRegion, Query> createQueryList(
            Collection<GenomicRegion> genomicRegions, int extension, String organismName,
            Set<Class<?>> featureTypes, boolean strandSpecific) {
        return createRegionQueries(genomicRegions, extension, organismName, featureTypes,
                strandSpecific, false);
    }

    /**
     * To extend genomic region
     * @param gr GenomicRegion
     * @return GenomicRegion
     */
    private static GenomicRegion extendGenomicRegion(GenomicRegion gr, int extension) {

        int min = 1;

        int start = gr.getStart();
        int end = gr.getEnd();

        int extendedStart = start - extension;
        int extendedEnd = end + extension;

        if (extendedStart < min) {
            gr.setExtendedStart(min);
        } else {
            gr.setExtendedStart(extendedStart);
        }

        gr.setExtendedEnd(extendedEnd);

        return gr;
    }

    /**
     * Generate a GenomicRegion object from region strings and relevant
     * information
     *
     * @param genomicRegionStringCollection
     *            a list of string such as 2L:14615455..14619002|0|D. melanogaster or
     *            2L:14615456..14619003|2L:14615455..14619002|1|D. melanogaster
     * @return a GenomicRegion object
     * @throws Exception with error message
     */
    public static List<GenomicRegion> generateGenomicRegions(
            Collection<String> genomicRegionStringCollection) throws Exception {

        List<GenomicRegion> genomicRegionList = new ArrayList<GenomicRegion>();

        for (String genomicRegionString : genomicRegionStringCollection) {

            String original;
            String extended;
            String extenedSize;
            String organism;

            String[] grInfo = genomicRegionString.trim().split("\\|");

            if (grInfo.length == 3) {
                original = grInfo[0];
                extended = null;
                extenedSize = grInfo[1];
                organism = grInfo[2];
            } else if (grInfo.length == 4) {
                original = grInfo[1];
                extended = grInfo[0];
                extenedSize = grInfo[2];
                organism = grInfo[3];
            } else {
                throw new Exception("Genomic region info error: " + genomicRegionString);
            }

            GenomicRegion gr = new GenomicRegion();

            if (organism == null || original == null) {
                throw new Exception("Organism and Original genomic region string can not be null");
            }

            if (extended == null) {
                Matcher m = DOT_DOT.matcher(original);
                if (m.find()) {
                    String chr = original.split(":")[0];
                    String start = original.split(":")[1].split("\\.{2}")[0];
                    String end = original.split(":")[1].split("\\.{2}")[1];

                    gr.setOrganism(organism);
                    gr.setChr(chr);
                    gr.setStart(Integer.valueOf(start));
                    gr.setEnd(Integer.valueOf(end));
                    gr.setExtendedRegionSize(0);
                    gr.setMinusStrand(gr.getStart() > gr.getEnd());
                    genomicRegionList.add(gr);
                } else {
                    throw new Exception("Not Dot-Dot format: " + original);
                }
            } else {
                if (extenedSize == null) {
                    throw new Exception("extenedSize can not be null");
                } else {
                    Matcher mo = DOT_DOT.matcher(original);
                    Matcher me = DOT_DOT.matcher(extended);
                    if (mo.find() && me.find()) {
                        String chr = original.split(":")[0];
                        String start = original.split(":")[1].split("\\.{2}")[0];
                        String end = original.split(":")[1].split("\\.{2}")[1];
                        String extStart = extended.split(":")[1].split("\\.{2}")[0];
                        String extEnd = extended.split(":")[1].split("\\.{2}")[1];

                        gr.setOrganism(organism);
                        gr.setChr(chr);
                        gr.setStart(Integer.valueOf(start));
                        gr.setEnd(Integer.valueOf(end));
                        gr.setExtendedStart(Integer.valueOf(extStart));
                        gr.setExtendedEnd(Integer.valueOf(extEnd));
                        gr.setExtendedRegionSize(Integer.valueOf(extenedSize));
                        gr.setMinusStrand(gr.getStart() > gr.getEnd());
                        genomicRegionList.add(gr);
                    } else {
                        throw new Exception("Not Dot-Dot format: " + original);
                    }
                }
            }
        }

        return genomicRegionList;
    }

    /**
     * Create a list of GenomicRegion objects from a collection of region strings
     * @param regionStringList list of region strings
     * @param organism short name
     * @param extendedRegionSize flanking
     * @param isInterBaseCoordinate inter base
     * @return a list of GenomicRegion objects
     */
    public static List<GenomicRegion> createGenomicRegionsFromString(
            Collection<String> regionStringList, String organism, Integer extendedRegionSize,
            Boolean isInterBaseCoordinate) {
        List<GenomicRegion> grList = new ArrayList<GenomicRegion>();
        for (String grStr : regionStringList) {
            GenomicRegion aSpan = new GenomicRegion();
            aSpan.setOrganism(organism);
            if (extendedRegionSize != null) {
                aSpan.setExtendedRegionSize(extendedRegionSize);
            }

            if (DOT_DOT.matcher(grStr).find()) {
                aSpan.setChr((grStr.split(":"))[0]);
                String[] spanItems = (grStr.split(":"))[1].split("\\..");
                String start = spanItems[0].trim();
                if (isInterBaseCoordinate) {
                    aSpan.setStart(Integer.valueOf(start) + 1);
                } else {
                    aSpan.setStart(Integer.valueOf(start));
                }
                aSpan.setEnd(Integer.valueOf(spanItems[1]));
            } else if (BED.matcher(grStr).find()) {
                String[] spanItems = grStr.split("\t");
                aSpan.setChr(spanItems[0]);
                if (isInterBaseCoordinate) {
                    aSpan.setStart(Integer.valueOf(spanItems[1]) + 1);
                } else {
                    aSpan.setStart(Integer.valueOf(spanItems[1]));
                }
                aSpan.setEnd(Integer.valueOf(spanItems[2]));
            } else if (DASH.matcher(grStr).find()) {
                aSpan.setChr((grStr.split(":"))[0]);
                String[] spanItems = (grStr.split(":"))[1].split("-");
                String start = spanItems[0].trim();
                if (isInterBaseCoordinate) {
                    aSpan.setStart(Integer.valueOf(start) + 1);
                } else {
                    aSpan.setStart(Integer.valueOf(start));
                }
                aSpan.setEnd(Integer.valueOf(spanItems[1]));
            } else if (SINGLE_POS.matcher(grStr).find()) {
                aSpan.setChr((grStr.split(":"))[0]);
                String start = (grStr.split(":"))[1].trim();
                if (isInterBaseCoordinate) {
                    aSpan.setStart(Integer.valueOf(start) + 1);
                } else {
                    aSpan.setStart(Integer.valueOf(start));
                }
                aSpan.setEnd(Integer.valueOf((grStr.split(":"))[1].trim()));
            } else {
                throw new IllegalArgumentException("Region string is in wrong format: " + grStr);
            }
            aSpan.setMinusStrand(aSpan.getStart() > aSpan.getEnd());
            grList.add(aSpan);
        }
        return grList;
    }

    /**
     * Look for genomic regions within or overlap an interval.
     *
     * @param interval the given interval
     * @param regionSet a set of regions
     * @return a list of genomic region objects
     * @throws Exception with error message
     */
    public static List<GenomicRegion> groupGenomicRegionByInterval(String interval,
        Set<GenomicRegion> regionSet) throws Exception {

        // Parse the interval
        Matcher m = DOT_DOT.matcher(interval);
        if (m.find()) {
            String chr = interval.split(":")[0];
            int start = Integer.valueOf(interval.split(":")[1].split("\\.{2}")[0]);
            int end = Integer.valueOf(interval.split(":")[1].split("\\.{2}")[1]);

            List<GenomicRegion> filteredList = new ArrayList<GenomicRegion>();

            for (GenomicRegion gr : regionSet) {
                if (chr.equals(gr.getChr())) {
                    if (gr.getExtendedRegionSize() > 0) {
                        if (!((gr.getExtendedStart() < start && gr
                               .getExtendedEnd() < end) && (gr
                                                            .getExtendedStart() > start && gr
                                                            .getExtendedEnd() > end))) {
                            filteredList.add(gr);
                        }
                    } else {
                        if (!((gr.getStart() < start && gr.getEnd() < end)
                                && (gr.getStart() > start && gr.getEnd() > end))) {
                            filteredList.add(gr);
                        }
                    }
                }
            }

            return filteredList;

        } else {
            throw new Exception("Not Dot-Dot format: " + interval);
        }
    }

}

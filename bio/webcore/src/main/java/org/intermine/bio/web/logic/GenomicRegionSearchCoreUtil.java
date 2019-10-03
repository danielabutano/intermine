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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.intermine.bio.web.model.ChromosomeInfo;
import org.intermine.bio.web.model.GenomicRegion;
import org.intermine.metadata.ConstraintOp;
import org.intermine.model.bio.Chromosome;
import org.intermine.model.bio.Location;
import org.intermine.model.bio.Organism;
import org.intermine.model.bio.SequenceFeature;
import org.intermine.objectstore.query.BagConstraint;
import org.intermine.objectstore.query.ConstraintSet;
import org.intermine.objectstore.query.ContainsConstraint;
import org.intermine.objectstore.query.OverlapConstraint;
import org.intermine.objectstore.query.OverlapRange;
import org.intermine.objectstore.query.Query;
import org.intermine.objectstore.query.QueryClass;
import org.intermine.objectstore.query.QueryField;
import org.intermine.objectstore.query.QueryObjectReference;
import org.intermine.objectstore.query.QueryValue;
import org.intermine.objectstore.query.SimpleConstraint;

/**
 * This utility class instance a GenomicRegionSearchService object based on mine's setting.
 *
 * @author Fengyuan Hu
 * @author Daniela Butano
 */
public abstract class GenomicRegionSearchCoreUtil
{
    protected static final Pattern DOT_DOT =
            Pattern.compile("[^:]+: ?\\d+\\.{2}\\d+$"); // "chr:start..end"
    protected static final Pattern BED =
            Pattern.compile("[^\\t\\s]+\\t\\d+\\t\\d+"); // "chr(tab)start(tab)end"
    protected static final Pattern DASH =
            Pattern.compile("[^:]+: ?\\d+\\-\\d+$"); // "chr:start-end"
    protected static final Pattern SINGLE_POS =
            Pattern.compile("[^:]+: ?\\d+$"); // "chr:singlePosition" - [^:]+:[\d]+$

    /**
     * Default constructor
     */
    protected GenomicRegionSearchCoreUtil() {
    }
    /**
     * Parse region from string to GenomicRegion object
     *
     * @param span a region string
     * @param isInterbase 0/1-based
     * @param chromsForOrg chr-organism info map
     * @return GenomicRegion object
     * @throws RegionParseException a RegionParseException
     */
    public static GenomicRegion parseRegion(String span, boolean isInterbase,
                                            Map<String, ChromosomeInfo> chromsForOrg)
            throws RegionParseException {

        String[] parts = parseDotDotSpan(span);
        if (parts == null) {
            parts = parseBedSpan(span);
        }
        if (parts == null) {
            parts = parseDashSpan(span);
        }
        if (parts == null) {
            parts = parseSinglePositionSpan(span);
        }
        if (parts == null) {
            throw new RegionParseException("Span format not recognised");
        }

        GenomicRegion region = new GenomicRegion();
        region.setChr(parts[0].trim());
        int start = Integer.valueOf(parts[1].trim()),
                end = Integer.valueOf(parts[2].trim());
        if (isInterbase) {
            region.setStart(start + 1);
        } else {
            region.setStart(start);
        }
        region.setEnd(end);

        region.setMinusStrand(start > end);

        ChromosomeInfo ci = getChromosomeInfo(chromsForOrg, region.getChr());

        if ((region.getStart() >= 1 && region.getStart() <= ci.getChrLength())
                && (region.getEnd() >= 1 && region.getEnd() <= ci.getChrLength())) {
            if (region.getStart() > region.getEnd()) {
                // Swap them around.
                int oldStart = region.getStart(), oldEnd = region.getEnd();
                region.setStart(oldStart);
                region.setEnd(oldEnd);
            }
            region.setChr(ci.getChrPID());
        } else {
            throw new RegionParseException("start and/or end values are out of bounds "
                    + "(0 - " + ci.getChrLength() + ")");
        }

        return region;
    }

    private static ChromosomeInfo getChromosomeInfo(Map<String, ChromosomeInfo> chromsForOrg,
                                                    String chromosome)
            throws RegionParseException {
        String chr = chromosome.toLowerCase();
        if (chromsForOrg.containsKey(chr)) {
            return chromsForOrg.get(chr);
        } else {
            if (chr.startsWith("chr")) {
                if (chromsForOrg.containsKey(chr.substring(3))) {
                    return chromsForOrg.get(chr.substring(3));
                }
            }
        }
        throw new RegionParseException(chr + " does not match any chromosome in this organism");
    }

    private static String[] parseDotDotSpan(String span) {
        Matcher m = DOT_DOT.matcher(span);
        if (m.find()) {
            String[] chr = new String[]{span.split(":")[0]};
            return (String[]) ArrayUtils.addAll(chr, span.split(":")[1].split("\\.{2}"));
        } else {
            return null;
        }
    }

    private static String[] parseBedSpan(String span) {
        Matcher m = BED.matcher(span);
        if (m.find()) {
            return span.split("\t");
        } else {
            return null;
        }
    }

    private static String[] parseDashSpan(String span) {
        Matcher m = DASH.matcher(span);
        if (m.find()) {
            String[] chr = new String[]{span.split(":")[0]};
            return (String[]) ArrayUtils.addAll(chr, span.split(":")[1].split("-"));
        } else {
            return null;
        }
    }

    private static String[] parseSinglePositionSpan(String span) {
        Matcher m = SINGLE_POS.matcher(span);
        String[] ret = new String[3];
        if (m.find()) {
            ret[0] = span.split(":")[0];
            ret[1] = (span.split(":"))[1];
            ret[2] = ret[1];
            return ret;
        } else {
            return null;
        }
    }

    /**
     * Create a list of queries from user regions.
     *
     * @param genomicRegions list of gr
     * @param extension the flanking
     * @param chromInfo chr info map
     * @param organismName org short name
     * @param featureTypes ft
     * @param strandSpecific flag
     * @return map of gr-query
     */
    public static Map<GenomicRegion, Query> createRegionListQueries(
            Collection<GenomicRegion> genomicRegions, int extension,
            Map<String, ChromosomeInfo> chromInfo, String organismName, Set<Class<?>> featureTypes,
            boolean strandSpecific) {
        return createRegionQueries(genomicRegions, extension, organismName, featureTypes,
                strandSpecific, true);
    }

    /**
     * Create queries from user regions.
     *
     * @param genomicRegions list of gr
     * @param extension the flanking
     * @param organismName org short name
     * @param featureTypes ft
     * @param strandSpecific flag
     * @param idOnly flag
     * @return map of gr-query
     */
    protected static Map<GenomicRegion, Query> createRegionQueries(
            Collection<GenomicRegion> genomicRegions, int extension, String organismName,
            Set<Class<?>> featureTypes, boolean strandSpecific, boolean idOnly) {

        Map<GenomicRegion, Query> queryMap = new LinkedHashMap<GenomicRegion, Query>();

        for (GenomicRegion aSpan : genomicRegions) {

            Integer start;
            Integer end;

            if (extension > 0) {
                aSpan = extendGenomicRegion(aSpan, extension);
                start = aSpan.getExtendedStart();
                end = aSpan.getExtendedEnd();
            } else {
                start = aSpan.getStart();
                end = aSpan.getEnd();
            }

            Query q = new Query();
            q.setDistinct(true);

            String chrPID = aSpan.getChr();

            QueryClass qcOrg = new QueryClass(Organism.class);
            QueryClass qcChr = new QueryClass(Chromosome.class);
            QueryClass qcFeature = new QueryClass(SequenceFeature.class);
            QueryClass qcLoc = new QueryClass(Location.class);

            QueryField qfOrgName = new QueryField(qcOrg, "shortName");

            QueryField qfFeatureId = new QueryField(qcFeature, "id");
            QueryField qfFeaturePID = new QueryField(qcFeature, "primaryIdentifier");
            QueryField qfFeatureSymbol = new QueryField(qcFeature, "symbol");
            QueryField qfFeatureClass = new QueryField(qcFeature, "class");

            QueryField qfChr = new QueryField(qcChr, "primaryIdentifier");

            QueryField qfLocStart = new QueryField(qcLoc, "start");
            QueryField qfLocEnd = new QueryField(qcLoc, "end");
            QueryField qfLocStrand = new QueryField(qcLoc, "strand");

            q.addToSelect(qfFeatureId);
            q.addFrom(qcFeature);
            q.addFrom(qcChr);
            q.addFrom(qcOrg);
            q.addFrom(qcLoc);
            if (!idOnly) {
                q.addToSelect(qfFeaturePID);
                q.addToSelect(qfFeatureSymbol);
                q.addToSelect(qfFeatureClass);
                q.addToSelect(qfChr);
                q.addToSelect(qfLocStart);
                q.addToSelect(qfLocEnd);
                q.addToSelect(qfLocStrand);
                q.addToOrderBy(qfLocStart, "ascending");
            }

            ConstraintSet constraints = new ConstraintSet(ConstraintOp.AND);

            q.setConstraint(constraints);

            // SequenceFeature.organism = Organism
            QueryObjectReference organism = new QueryObjectReference(qcFeature, "organism");
            ContainsConstraint ccOrg = new ContainsConstraint(organism, ConstraintOp.CONTAINS,
                    qcOrg);
            constraints.addConstraint(ccOrg);

            // Organism.name = orgName
            SimpleConstraint scOrg = new SimpleConstraint(qfOrgName, ConstraintOp.EQUALS,
                    new QueryValue(organismName));
            constraints.addConstraint(scOrg);

            // Location.feature = SequenceFeature
            QueryObjectReference locSubject = new QueryObjectReference(qcLoc, "feature");
            ContainsConstraint ccLocSubject = new ContainsConstraint(locSubject,
                    ConstraintOp.CONTAINS, qcFeature);
            constraints.addConstraint(ccLocSubject);

            // Location.locatedOn = Chromosome
            QueryObjectReference locObject = new QueryObjectReference(qcLoc, "locatedOn");
            ContainsConstraint ccLocObject = new ContainsConstraint(locObject,
                    ConstraintOp.CONTAINS, qcChr);
            constraints.addConstraint(ccLocObject);

            // Location.strand = strand (optional)
            if (strandSpecific) {
                String strand = "1";
                if (aSpan.getMinusStrand()) {
                    strand = "-1";
                }
                SimpleConstraint scStrand = new SimpleConstraint(qfLocStrand, ConstraintOp.EQUALS,
                        new QueryValue(strand));
                constraints.addConstraint(scStrand);
            }

            // Chromosome.primaryIdentifier = chrPID
            SimpleConstraint scChr = new SimpleConstraint(qfChr, ConstraintOp.EQUALS,
                    new QueryValue(chrPID));
            constraints.addConstraint(scChr);

            // SequenceFeature.class in a list
            constraints.addConstraint(new BagConstraint(qfFeatureClass, ConstraintOp.IN,
                    featureTypes));

            OverlapRange overlapInput = new OverlapRange(new QueryValue(start),
                    new QueryValue(end), locObject);
            OverlapRange overlapFeature = new OverlapRange(new QueryField(qcLoc, "start"),
                    new QueryField(qcLoc, "end"), locObject);
            OverlapConstraint oc = new OverlapConstraint(overlapInput, ConstraintOp.OVERLAPS,
                    overlapFeature);
            constraints.addConstraint(oc);

            queryMap.put(aSpan, q);
        }

        return queryMap;
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
}

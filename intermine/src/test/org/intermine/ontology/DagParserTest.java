package org.flymine.ontology;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import junit.framework.*;

import java.io.StringReader;
import java.io.BufferedReader;
import java.util.Set;

public class DagParserTest extends TestCase
{
    private DagParser parser;

    public DagParserTest(String arg) {
        super(arg);
    }

    public void setUp() {
        parser = new DagParser();
    }

    public void testProcess() throws Exception {
        String test = "!Test Ontology\n"
            + "$Test Ontology ; id0\n"
            + " <term1 ; id1\n"
            + "  %term2 ; id2\n"
            + "   %term3 ; id3\n"
            + "    <term4 ; id4\n"
            + "  %term5 ; id5\n";

        Set terms = parser.process(new BufferedReader(new StringReader(test)));
        assertEquals(1, terms.size());

        DagTerm domain = (DagTerm) terms.iterator().next();
        assertEquals(0, domain.getChildren().size());
        assertEquals(1, domain.getComponents().size());
    }

    public void testReadTerms() throws Exception {
        String test = "!Test Ontology\n"
            + "$Test Ontology ; id0\n"
            + " <term1 ; id1\n"
            + "  %term2 ; id2\n"
            + "   %term3 ; id3\n"
            + "    <term4 ; id4\n"
            + "  %term5 ; id5\n";

        parser.readTerms(new BufferedReader(new StringReader(test)));

        assertEquals(1, parser.terms.size());

        DagTerm domain = (DagTerm) parser.terms.iterator().next();
        assertEquals(0, domain.getChildren().size());
        assertEquals(1, domain.getComponents().size());

        assertEquals(6, parser.seenTerms.size());
        DagParser.Identifier i1 = parser.new Identifier("id1", "term1");
        DagTerm term1 = (DagTerm) parser.seenTerms.get(i1);
        assertEquals(2, term1.getChildren().size());
        assertEquals(0, term1.getComponents().size());

        DagParser.Identifier i2 = parser.new Identifier("id2", "term2");
        DagTerm term2 = (DagTerm) parser.seenTerms.get(i2);
        assertEquals(1, term2.getChildren().size());
        assertEquals(0, term2.getComponents().size());

        DagParser.Identifier i3 = parser.new Identifier("id3", "term3");
        DagTerm term3 = (DagTerm) parser.seenTerms.get(i3);
        assertEquals(0, term3.getChildren().size());
        assertEquals(1, term3.getComponents().size());

        DagParser.Identifier i4 = parser.new Identifier("id4", "term4");
        DagTerm term4 = (DagTerm) parser.seenTerms.get(i4);
        assertEquals(0, term4.getChildren().size());
        assertEquals(0, term4.getComponents().size());

        DagParser.Identifier i5 = parser.new Identifier("id5", "term5");
        DagTerm term5 = (DagTerm) parser.seenTerms.get(i5);
        assertEquals(0, term5.getChildren().size());
        assertEquals(0, term5.getComponents().size());
    }

    public void testMakeDagTermIsa() throws Exception {
        String test = "%name ; id";

        DagTerm parent = new DagTerm("id1", "parent");
        parser.parents.push(parent);

        DagTerm term = new DagTerm("id", "name");

        assertTrue(parent.getChildren().size() == 0);
        assertTrue(term.equals(parser.makeDagTerm(test)));
        assertTrue(parent.getChildren().size() == 1);
    }

    public void makeDagTermPartOf() throws Exception {
        String test = "<name ; id";

        DagTerm whole = new DagTerm("id1", "whole");
        parser.parents.push(whole);

        DagTerm term = new DagTerm("id", "name");

        assertTrue(whole.getComponents().size() == 0);
        assertTrue(term.equals(parser.makeDagTerm(test)));
        assertTrue(whole.getComponents().size() == 1);
    }

    public void makeDagTermDomain() throws Exception {
        String test = "$name ; id % parent1 ; id2\n";

        DagTerm term = new DagTerm("id", "name");

        assertTrue(term.equals(parser.makeDagTerm(test)));
        assertEquals(1, parser.terms.size());
        assertEquals(2, parser.seenTerms.size());
    }

    public void testMakeDagTermExtraRelations() throws Exception {
        String test = "%name ; id % parent2 ; id2 < whole1 ; id3";

        DagTerm parent1 = new DagTerm("id1", "parent1");
        parser.parents.push(parent1);

        DagTerm term = new DagTerm("id", "name");

        assertTrue(parent1.getChildren().size() == 0);
        assertTrue(term.equals(parser.makeDagTerm(test)));
        assertTrue(parent1.getChildren().size() == 1);

        DagParser.Identifier i2 = parser.new Identifier("id2", "parent2");
        DagTerm parent2 = (DagTerm) parser.seenTerms.get(i2);
        assertNotNull(parent2);
        assertEquals(1, parent2.getChildren().size());
        DagParser.Identifier i3 = parser.new Identifier("id3", "whole1");
        DagTerm whole1 = (DagTerm) parser.seenTerms.get(i3);
        assertNotNull(whole1);
        assertEquals(1, whole1.getComponents().size());
    }

    public void testDagTermFromString() throws Exception {
        String test = "name ; id ; synonym:s1 ; synonym:s2";

        DagTerm term = new DagTerm("id", "name");
        term.addSynonym("s1");
        term.addSynonym("s2");

        assertTrue(term.equals(parser.dagTermFromString(test)));
        assertTrue(parser.seenTerms.size() == 1);
    }

    public void testDagTermFromStringSeen() throws Exception {
        String test = "name ; id ; synonym:s1 ; synonym:s2";

        DagTerm seen = new DagTerm("id", "name");
        seen.addSynonym("old1");
        DagTerm child = new DagTerm("id1", "child1");
        seen.addChild(child);
        DagParser.Identifier identifier = parser.new Identifier("id", "name");
        parser.seenTerms.put(identifier, seen);

        DagTerm term = new DagTerm("id", "name");
        term.addSynonym("s1");
        term.addSynonym("s2");
        term.addSynonym("old1");
        term.addChild(child);

        assertTrue(term.equals(parser.dagTermFromString(test)));
        assertTrue(parser.seenTerms.size() == 1);
    }

    public void testDagFromStringNoId() throws Exception {
        String test = "name ; ";


        try {
            parser.dagTermFromString(test);
            fail("Expected exception");
        } catch (Exception e) {
        }
    }

    public void testIdentifierEquals() throws Exception {
        String name1 = "name1";
        String name2 = "name2";
        String id1 = "id1";
        String id2 = "id2";

        DagParser.Identifier i1 = parser.new Identifier(id1, name1);
        DagParser.Identifier i2 = parser.new Identifier(id2, name1);
        DagParser.Identifier i3 = parser.new Identifier(id1, name2);
        DagParser.Identifier i4 = parser.new Identifier(id2, name2);
        DagParser.Identifier i5 = parser.new Identifier(id1, name1);

        assertTrue(i1.equals(i5));
        assertTrue(i5.equals(i1));
        assertFalse(i1.equals(i2));
        assertFalse(i1.equals(i3));
        assertFalse(i1.equals(i4));
    }

//     public void testStripEscaped() throws Exception {
//         assertEquals("%�", parser.stripEscaped("\\%\\�"));
//         assertEquals("a\\d", parser.stripEscaped("a\\d"));
//     }

    public void testTrimLeft() throws Exception {
        assertEquals("string  ", parser.trimLeft("  string  "));
        assertEquals("string  ", parser.trimLeft("string  "));
        assertEquals("", parser.trimLeft(""));

    }

    public void testSpecificNameProblems() throws Exception {
        String test = "positive regulation of transcription from Pol II promoter ; GO:0045944";

        DagTerm term = new DagTerm("GO:0045944", "positive regulation of transcription from Pol II promoter");

        assertTrue(term.equals(parser.dagTermFromString(test)));
    }


}

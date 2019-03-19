package org.intermine.web.logic;

import junit.framework.TestCase;
import org.intermine.metadata.Model;
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.pathquery.PathQuery;
import org.intermine.web.logic.config.WebConfig;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class WebCoreUtilTest extends TestCase {
    MockServletContext context = new MockServletContext();
    WebConfig config;
    Model model;

    public WebCoreUtilTest(final String arg) throws FileNotFoundException,
            IOException, SAXException, ClassNotFoundException {
        super(arg);

        final Properties p = new Properties();
        p.setProperty("web.config.classname.mappings", "CLASS_NAME_MAPPINGS");
        p.setProperty("web.config.fieldname.mappings", "FIELD_NAME_MAPPINGS");
        context.setAttribute(Constants.WEB_PROPERTIES, p);

        final InputStream is = getClass().getClassLoader().getResourceAsStream(
                "WebConfigTest.xml");
        final InputStream classesIS = getClass().getClassLoader()
                .getResourceAsStream("testClassMappings.properties");
        final InputStream fieldsIS = getClass().getClassLoader()
                .getResourceAsStream("testFieldMappings.properties");
        context.addInputStream("/WEB-INF/webconfig-model.xml", is);
        context.addInputStream("/WEB-INF/CLASS_NAME_MAPPINGS", classesIS);
        context.addInputStream("/WEB-INF/FIELD_NAME_MAPPINGS", fieldsIS);

        model = Model.getInstanceByName("testmodel");
        config = WebConfig.parse(context, model);
    }

    public void testFormatPath() throws PathException {
        Path p = new Path(model, "Employee.name");
        String expected = "Angestellter > Name";
        // Check class name labels
        assertEquals(expected, WebCoreUtil.formatPath(p, config));

        p = new Path(model, "Contractor.oldComs.vatNumber");
        // Check reference and attribute labels
        expected = "Contractor > Companies they used to work for > VAT Number";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));

        p = new Path(model, "Contractor.personalAddress.address");
        // Check default munging
        expected = "Contractor > Personal Address > Address";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));

        // Check path making
        expected = "Contractor > Companies they used to work for > VAT Number";
        assertEquals(expected, WebCoreUtil.formatPath(
                "Contractor.oldComs.vatNumber", model, config));

        // Check path making, complete labelling
        expected = "Firma > Abteilungen > Angestellter";
        assertEquals(expected, WebCoreUtil.formatPath(
                "Company.departments.employees", model, config));

        // Check composite attribute labelling
        p = new Path(model, "Employee.department.name");
        expected = "Angestellter > Abteilung";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));

        // Check composite attribute labelling, from a reference.
        p = new Path(model, "Manager.department.employees.department.name");
        expected = "Manager > Department > Angestellter > Abteilung";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));
    }

    public void testFormatField() throws PathException {
        Path p = new Path(model, "Employee.name");
        String expected = "Name";
        assertEquals(expected, WebCoreUtil.formatField(p, config));

        p = new Path(model, "Contractor.oldComs.vatNumber");
        expected = "VAT Number";
        assertEquals(expected, WebCoreUtil.formatField(p, config));

        p = new Path(model, "Contractor.personalAddress");
        expected = "Personal Address";
        assertEquals(expected, WebCoreUtil.formatField(p, config));
    }

    public void testSubclassedPath() throws PathException {
        Path p;
        String expected;

        p = new Path(model, "Department.employees[Manager].seniority");
        expected = "Abteilung > Angestellter > Seniority";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));

        p = new Path(model, "Company.departments.employees[Manager].seniority");
        expected = "Firma > Abteilungen > Angestellter > Seniority";
        assertEquals(expected, WebCoreUtil.formatPath(p, config));
    }

    public void testFormatPathDescription() {
        final PathQuery pq = new PathQuery(model);
        pq.setDescription("Employee.department.company", "COMPANY");
        pq.setDescription("Employee.department", "DEPARTMENT");
        pq.setDescription("Employee", "EMPLOYEE");

        assertEquals("EMPLOYEE",
                WebCoreUtil.formatPathDescription("Employee", pq, config));

        assertEquals("EMPLOYEE > Years Alive",
                WebCoreUtil.formatPathDescription("Employee.age", pq, config));

        assertEquals("EMPLOYEE > Address > Address",
                WebCoreUtil.formatPathDescription("Employee.address.address", pq,
                        config));

        assertEquals("EMPLOYEE > Full Time",
                WebCoreUtil.formatPathDescription("Employee.fullTime", pq, config));

        assertEquals("DEPARTMENT", WebCoreUtil.formatPathDescription(
                "Employee.department", pq, config));
    }

    public void testCompositePathDescriptions() {
        final PathQuery pq = new PathQuery(model);
        pq.setDescription("Employee.department.company", "COMPANY");
        pq.setDescription("Employee.department", "DEPARTMENT");
        pq.setDescription("Employee.address", "RESIDENCE");
        pq.setDescription("Employee", "EMPLOYEE");

        assertEquals("RESIDENCE > Address", WebCoreUtil.formatPathDescription(
                "Employee.address.address", pq, config));

        // Obeys existing composite rules for attributes.
        assertEquals("DEPARTMENT", WebCoreUtil.formatPathDescription(
                "Employee.department.name", pq, config));

        assertEquals("DEPARTMENT > Manager > Name",
                WebCoreUtil.formatPathDescription(
                        "Employee.department.manager.name", pq, config));

        assertEquals("COMPANY", WebCoreUtil.formatPathDescription(
                "Employee.department.company", pq, config));

        assertEquals("COMPANY > Abteilungen > Manager > Years Alive",
                WebCoreUtil.formatPathDescription(
                        "Employee.department.company.departments.manager.age",
                        pq, config));

        // Paths without any configuration are handled as per formatPath
        assertEquals("Contractor > Companies they used to work for > VAT Number",
                WebCoreUtil.formatPathDescription("Contractor.oldComs.vatNumber", pq, config));
    }

    /**
     * Check that formatted pathquery views take both the pathdescriptions and the webconfig into account.
     */
    public void testFormatPathQueryView() {
        final PathQuery pq = new PathQuery(model);
        pq.setDescription("Employee.department.company", "COMPANY");
        pq.setDescription("Employee.department", "DEPARTMENT");
        pq.setDescription("Employee", "EMPLOYEE");
        pq.addViews("Employee.name", "Employee.fullTime", "Employee.department.name",
                "Employee.department.company.contractors.oldComs.vatNumber");

        final List<String> expected = Arrays.asList("EMPLOYEE > Name", "EMPLOYEE > Full Time",
                "DEPARTMENT", "COMPANY > Contractors > Companies they used to work for > VAT Number");
        assertEquals(expected, WebCoreUtil.formatPathQueryView(pq, config));
    }

}

package org.intermine.web.logic;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockServletContext implements ServletContext {
    private Map<String, InputStream> inputStreams = new HashMap<String, InputStream>();
    protected HashMap attributes = new HashMap();

    public MockServletContext() {
        // Auto-generated constructor stub
    }

    public void addInputStream(String resourceName, InputStream is) {
        inputStreams.put(resourceName, is);
    }

    public InputStream getResourceAsStream(String name) {
        return inputStreams.get(name);
    }

    @Override
    public ServletContext getContext(String uripath) {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public String getMimeType(String file) {
        return null;
    }

    @Override
    public Set getResourcePaths(String path) {
        return null;
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        return null;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    public String getServerInfo() {
        return ("MockServletContext/$Version$");
    }

    @Override
    public String getInitParameter(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration getInitParameterNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAttribute(String name) {
        return (attributes.get(name));
    }

    @Override
    public Enumeration getAttributeNames() { return null; }

    @Override
    public void setAttribute(String name, Object object) {
        if (object == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, object);
        }
    }

    @Override
    public void removeAttribute(String name) {

    }

    public Servlet getServlet(String name) {
        throw new UnsupportedOperationException();
    }

    public String getServletContextName() {
        return (getServerInfo());
    }

    public Enumeration getServletNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void log(String msg) {}

    @Override
    public void log(Exception exception, String msg) {}

    @Override
    public void log(String message, Throwable throwable) {}

    @Override
    public String getRealPath(String path) {
        return null;
    }

    public Enumeration getServlets() {
        throw new UnsupportedOperationException();
    }
}

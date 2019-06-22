package org.intermine.webservice.util;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;


/**
 * Response util that sets content type and header for various formats and has
 * util methods for setting headers controlling cache.
 * @author Jakub Kulaviak
 **/
public final class ResponseUtilSpring
{
    public static final MediaType MEDIA_TYPE_TAB = new MediaType("text", "tab-separated-values", Charset.forName("utf-8"));
    public static final MediaType MEDIA_TYPE_CSV = new MediaType("text", "comma-separated-values", Charset.forName("utf-8"));
    public static final MediaType MEDIA_TYPE_JSON_SCHEMA = new MediaType("application", "schema+json", Charset.forName("utf-8"));
    public static final MediaType MEDIA_TYPE_JSONP = new MediaType("application", "javascript", Charset.forName("utf-8"));

    private ResponseUtilSpring() {
        // do nothing
    }

    /**
     * Sets response header and content type for tab separated
     * values output.
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     */
    public static void setTabHeader(HttpHeaders httpHeaders, String fileName) {
        setNoCache(httpHeaders);
        setTabContentType(httpHeaders);
        setFileName(httpHeaders, fileName);
    }

    /**
     * Sets response header and content type for comma separated
     * values output.
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     */
    public static void setCSVHeader(HttpHeaders httpHeaders, String fileName) {
        setNoCache(httpHeaders);
        setCSVContentType(httpHeaders);
        setFileName(httpHeaders, fileName);
    }

    /**
     * Sets response header and content type for XML output.
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     */
    public static void setXMLHeader(HttpHeaders httpHeaders, String fileName) {
        setNoCache(httpHeaders);
        setXMLContentType(httpHeaders);
        setFileName(httpHeaders, fileName);
    }


    /**
     * Sets response header and content type for plain text output.
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     */
    public static void setPlainTextHeader(HttpHeaders httpHeaders, String fileName) {
        setNoCache(httpHeaders);
        setPlainTextContentType(httpHeaders);
        setFileName(httpHeaders, fileName);
    }

    /**
     * Sets response header and content type for gzipped output.
     *
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     */
    public static void setGzippedHeader(HttpHeaders httpHeaders, String fileName) {
        setNoCache(httpHeaders);
        setGzippedContentType(httpHeaders);
        setFileName(httpHeaders, fileName);
    }

    /**
     * Sets the response header and content type for json output
     * @param httpHeaders headers
     * @param filename The filename this response should have
     */
    public static void setJSONHeader(HttpHeaders httpHeaders,
            String filename) {
        setJSONHeader(httpHeaders, filename, false);
    }

    /**
     * Sets the response header and content type for json output
     * @param httpHeaders headers
     * @param filename The filename this response should have
     * @param isJSONP Whether this request is being handled as JSONP
     */
    public static void setJSONHeader(HttpHeaders httpHeaders,
            String filename, boolean isJSONP) {
        if (isJSONP) {
            setJSONPHeader(httpHeaders, filename);
        } else {
            setJSONContentType(httpHeaders);
            setFileName(httpHeaders, filename);
            setNoCache(httpHeaders);
        }
    }

    /**
     * Set the header for a JSON-schema response.
     * @param httpHeaders headers
     * @param filename The file-name.
     */
    public static void setJSONSchemaHeader(HttpHeaders httpHeaders,
            String filename) {
        setJSONSchemaContentType(httpHeaders);
        setFileName(httpHeaders, filename);
        setNoCache(httpHeaders);
    }

    /**
     * Sets the response header and content type for jsonp output
     * @param httpHeaders headers
     * @param filename The name this response should have
     */
    public static void setJSONPHeader(HttpHeaders httpHeaders,
            String filename) {
        setJSONPContentType(httpHeaders);
        setFileName(httpHeaders, filename);
        setNoCache(httpHeaders);
    }

    /**
     * Sets response header and content type for a custom content type.
     * @param httpHeaders headers
     * @param fileName file name of downloaded file
     * @param contentType the content type to use
     */
    public static void setCustomTypeHeader(HttpHeaders httpHeaders, String fileName,
            String contentType) {
        setNoCache(httpHeaders);
        setCustomContentType(httpHeaders,contentType);
        setFileName(httpHeaders, fileName);
    }

    /**
     * Sets that the result must not be cached. Old implementation was set
     * Cache-Control to no-cache,no-store,max-age=0. But this caused problems
     * in IE. File couldn't be opened directly.
     * @param httpHeaders headers
     */
    public static void setNoCache(HttpHeaders httpHeaders) {
        // http://www.phord.com/experiment/cache/
        // http://support.microsoft.com/kb/243717
        httpHeaders.setPragma("no-cache");
        httpHeaders.setCacheControl("must-revalidate, max-age=0");
    }

    /**
     * Sets enforced no-cache headers to completely disable cache for this response.
     * Page is reloaded always, for example when the user uses Go Back button.
     * @param httpHeaders headers
     */
    public static void setNoCacheEnforced(HttpHeaders httpHeaders) {
        // should work for firefox and IE to refresh always the page when back button is pressed
        // http://forums.mozillazine.org/viewtopic.php?f=25&t=673135&start=30
        httpHeaders.setCacheControl("max-age=0, must-revalidate, no-store, no-cache");
        httpHeaders.setPragma("no-cache");
        httpHeaders.add("Expires", "Wed, 11 Jan 1984 05:00:00 GMT");
    }

    /**
     * Sets the content disposition filename.
     *
     * @param httpHeaders headers
     * @param fileName the name of the downloaded file
     */
    public static void setFileName(HttpHeaders httpHeaders, String fileName) {
        httpHeaders.setContentDispositionFormData("attachment",fileName);
    }


    /**
     * Sets tab separated values content type.
     * @param httpHeaders headers
     */
    public static void setTabContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MEDIA_TYPE_TAB);
    }

    /**
     * Sets comma separated values content type.
     * @param httpHeaders headers
     */
    public static void setCSVContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MEDIA_TYPE_CSV);
    }

    /**
     * Sets plain text content type.
     * @param httpHeaders headers
     */
    public static void setPlainTextContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
    }

    /**
     * Sets XML content type.
     * @param httpHeaders headers
     */
    public static void setXMLContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
    }

    /**
     * Sets HTML content type.
     * @param httpHeaders headers
     */
    public static void setHTMLContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.TEXT_HTML);
    }

    /**
     * Sets gzip content type ("application/octet-stream")
     * @param httpHeaders headers
     */
    public static void setGzippedContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    }

    /**
     * Sets content type to the parameter specified
     * @param httpHeaders headers
     * @param contentType custom MIME type to set as content type specified
     */
    public static void setCustomContentType(HttpHeaders httpHeaders, String contentType) {
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
    }

    /**
     * Sets the content type to "application/json"
     * @param httpHeaders headers
     */
    public static void setJSONContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Sets the content type to "application/schema+json"
     * @param httpHeaders headers
     */
    public static void setJSONSchemaContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MEDIA_TYPE_JSON_SCHEMA);
    }

    /**
     * Sets the content type to "text/javascript"
     * @param httpHeaders headers
     */
    public static void setJSONPContentType(HttpHeaders httpHeaders) {
        httpHeaders.setContentType(MEDIA_TYPE_JSONP);
    }

}


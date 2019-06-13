package org.intermine.webservice;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.TagManager;
import org.intermine.api.tag.TagTypes;
import org.intermine.metadata.*;
import org.intermine.objectstore.ObjectStoreSummary;
import org.intermine.pathquery.Path;
import org.intermine.pathquery.PathException;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.WebCoreUtil;
import org.intermine.web.logic.config.FieldConfig;
import org.intermine.web.logic.config.FieldConfigHelper;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;
import org.intermine.webservice.server.exceptions.ServiceException;
import org.intermine.webservice.server.output.JSONFormatter;
import org.intermine.webservice.util.ResponseUtilSpring;
import org.intermine.webservice.model.Model;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang.StringEscapeUtils.escapeJava;

@Service
public class ModelServiceSpring extends WebServiceSpring {


    public Model getResponseModel() {
        return responseModel;
    }

    private Model responseModel;

    private static final String DEFAULT_CALLBACK = "parseModel";
    private static final Logger LOG = Logger.getLogger(ModelServiceSpring.class);
    private static final String FILE_BASE_NAME = "model";
    private WebConfig config = null;
    private Path node = null;

    /**
     * Constructor.
     * @param im The API settings bundle
     */
    public ModelServiceSpring(InterMineAPI im) {
        super(im);
        config = InterMineContext.getWebConfig();
        responseModel = new Model();
    }


    @Override
    protected Format getDefaultFormat() {
        return Format.XML;
    }

    private static final String FORMAT_ENDINGS = "^/?(xml|tsv|csv|json|jsonp)$";

    @Override
    protected void initState() {
        super.initState();
        String pathInfo = StringUtils.defaultString(request.getPathInfo(), "");
        if (StringUtils.isBlank(pathInfo)) {
            return;
        }
        if (pathInfo.matches(FORMAT_ENDINGS)) {
            return;
        }
        setFormat(Format.JSON);
        pathInfo = StringUtil.trimSlashes(pathInfo).replace('/', '.');
        try {
            Map<String, String> subclasses = new HashMap<String, String>();
            for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements();) {
                String param = (String) e.nextElement();
                subclasses.put(param, request.getParameter(param));
            }
            Path p = new Path(im.getModel(), pathInfo, subclasses);
            node = p;
        } catch (PathException e) {
            throw new ResourceNotFoundException("Could not find a node with the id: " + pathInfo);
        }
    }

    @Override
    protected boolean canServe(Format format) {
        return format == Format.XML || format == Format.JSON;
    }


    /**
     * {@inheritDoc}}
     */
    @Override
    protected void execute() {
        final org.intermine.metadata.Model model = this.im.getModel();
        if (formatIsJSON()) {
            ResponseUtilSpring.setJSONHeader(responseHeaders, FILE_BASE_NAME + ".json", formatIsJSONP());
            if (formatIsJSONP()) {
                String callback = getCallback();
                if (callback == null || "".equals(callback)) {
                    callback = DEFAULT_CALLBACK;
                }
                responseHeaders.add(JSONFormatter.KEY_CALLBACK, callback);
            }
            if (node == null) {
                responseHeaders.add(JSONFormatter.KEY_INTRO, "\"model\":");
                responseModel.setModel(getAnnotatedModel(model));
            } else {
                Map<String, String> kvPairs = new HashMap<String, String>();
                kvPairs.put("name", getNodeName(node));
                kvPairs.put("id", node.toStringNoConstraints());
                kvPairs.put("display", WebCoreUtil.formatPath(node, config));
                kvPairs.put("type", "class");
                responseHeaders.add(JSONFormatter.KEY_KV_PAIRS, kvPairs.toString());
                responseHeaders.add(JSONFormatter.KEY_INTRO, "\"fields\":[");
                responseHeaders.add(JSONFormatter.KEY_OUTRO, "]");
                responseModel.setModel(nodeChildrenToJSON(node));
            }
        } else {
            responseModel.setModel(model.toString());
        }
    }

    private Map<String, Object> getAnnotatedModel(org.intermine.metadata.Model model) {
        TagManager tm = im.getTagManager();
        ObjectStoreSummary oss = im.getObjectStoreSummary();
        org.intermine.metadata.Model.ModelAST modelData = model.toJsonAST();
        Map<String, Map<String, Object>> classes = modelData.getClasses();
        Profile p = getPermission().getProfile();
        String userName = p.getUsername();
        try {
            Iterator<Map<String, Object>> iterator = classes.values().iterator();
            while (iterator.hasNext()) {
                Map<String, Object> classData = iterator.next();
                String className = (String) classData.get("name");
                // Might be a good idea to add in field names as well,
                // but these have sharper edge cases
                ClassDescriptor cd = model.getClassDescriptorByName(className);
                // Add the display name for this class.
                classData.put("displayName", WebCoreUtil.formatClass(cd, config));
                classData.put("term", cd.getFairTerm());
                String fullyQualifiedClassName = cd.getName();
                try {
                    // Add the count for this class.
                    classData.put("count", oss.getClassCount(fullyQualifiedClassName));
                } catch (RuntimeException e) {
                    LOG.error("No class count", e);
                }
                // Get the tags for this class.
                Set<String> tags = new HashSet<String>();
                if (p.isLoggedIn()) {
                    tags.addAll(tm.getObjectTagNames(fullyQualifiedClassName, TagTypes.CLASS,
                            userName));
                }
                tags.addAll(tm.getPublicTagNames(fullyQualifiedClassName, TagTypes.CLASS));
                classData.put("tags", tags);

                // Get the Attributes for this class.
                Object allAttributes = classData.get("attributes");
                Map<String, Object> attributes = (HashMap<String, Object>) allAttributes;
                Iterator<Map.Entry<String, Object>> attributesIterator
                        = attributes.entrySet().iterator();
                while (attributesIterator.hasNext()) {
                    Map.Entry<String, Object> entry = attributesIterator.next();
                    Map<String, Object> attribute = (HashMap<String, Object>) entry.getValue();
                    String attributeName = entry.getKey();
                    FieldDescriptor fd = cd.getAttributeDescriptorByName(attributeName, true);
                    String displayName = getDisplayName(cd, attributeName, fd);
                    attribute.put("displayName", displayName);
                    attribute.put("term", cd.getFairTerm());
                }

                // Get the refs for this class.
                Object allReferences = classData.get("references");
                Map<String, Object> refs = (HashMap<String, Object>) allReferences;
                Iterator<Map.Entry<String, Object>> refIterator = refs.entrySet().iterator();
                while (refIterator.hasNext()) {
                    Map.Entry<String, Object> entry = refIterator.next();
                    Map<String, Object> ref = (HashMap<String, Object>) entry.getValue();
                    String referenceName = entry.getKey();
                    FieldDescriptor fd = cd.getReferenceDescriptorByName(referenceName, true);
                    String displayName = getDisplayName(cd, referenceName, fd);
                    ref.put("displayName", displayName);
                }

                // Get the collections for this class.
                Object allCollections = classData.get("collections");
                Map<String, Object> collections = (HashMap<String, Object>) allCollections;
                Iterator<Map.Entry<String, Object>> collIterator = collections.entrySet().iterator();
                while (collIterator.hasNext()) {
                    Map.Entry<String, Object> entry = collIterator.next();
                    Map<String, Object> collection = (HashMap<String, Object>) entry.getValue();
                    String collectionName = entry.getKey();
                    FieldDescriptor fd = cd.getCollectionDescriptorByName(collectionName, true);
                    String displayName = getDisplayName(cd, collectionName, fd);
                    collection.put("displayName", displayName);
                }
            }
        } catch (RuntimeException t) {
            LOG.error("Could not annotate model", t);
            throw t;
        }
        return modelData;
    }

    private String getDisplayName(ClassDescriptor cd, String fieldName, FieldDescriptor fd) {
        String displayName = null;
        if (fd != null) {
            final FieldConfig fc = FieldConfigHelper.getFieldConfig(config, cd, fd);
            if (fc != null) {
                displayName = fc.getDisplayName();
            }
        }
        if (displayName == null) {
            displayName = FieldConfig.getFormattedName(fieldName);
        }
        return displayName;
    }

    private static String getNodeName(Path newNode) {
        WebConfig webConfig = InterMineContext.getWebConfig();
        if (newNode.isRootPath()) {
            return WebCoreUtil.formatPath(newNode, webConfig);
        } else {
            return WebCoreUtil.formatField(newNode, webConfig);
        }
    }

    private static List<String> nodeChildrenToJSON(Path newNode) {
        List<String> ret = new LinkedList<String>();
        if (!newNode.endIsAttribute()) {
            ClassDescriptor cd = newNode.getLastClassDescriptor();
            List<FieldDescriptor> fields = new LinkedList<FieldDescriptor>();
            fields.addAll(cd.getAllAttributeDescriptors());
            fields.addAll(cd.getAllReferenceDescriptors());
            fields.addAll(cd.getAllCollectionDescriptors());
            for (FieldDescriptor fd: fields) {
                try {
                    ret.add(fieldToJSON(newNode.append(fd.getName())));
                } catch (PathException e) {
                    throw new ServiceException("While walking model", e);
                }
            }
        }
        return ret;
    }

    private static String fieldToJSON(Path fieldPath) {

        StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"" + getNodeName(fieldPath) + "\"");
        sb.append(",");
        sb.append("\"id\":\"" + fieldPath.toStringNoConstraints() + "\"");
        if (!fieldPath.endIsAttribute()) {
            sb.append(",\"fields\":true");
            if (fieldPath.endIsCollection()) {
                sb.append(",\"type\":\"collection\"");
            } else {
                sb.append(",\"type\":\"reference\"");
            }
            sb.append(",\"references\":\""
                    + fieldPath.getLastClassDescriptor().getUnqualifiedName() + "\"");
        } else {
            String type = ((AttributeDescriptor) fieldPath.getEndFieldDescriptor()).getType();
            type = type.substring(type.lastIndexOf('.') + 1);
            sb.append(",\"type\":\"" + type + "\"");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void setFooter(){
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm::ss");
        String executionTime = dateFormatter.format(now);
        responseModel.setExecutionTime(executionTime);

        if (status >= 400) {
            responseModel.setWasSuccessful(false);
            responseModel.setError(escapeJava(errorMessage));
        } else {
            responseModel.setWasSuccessful(true);
        }
        responseModel.setStatusCode(status);
    }

}

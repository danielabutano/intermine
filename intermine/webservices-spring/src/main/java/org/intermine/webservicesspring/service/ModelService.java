package org.intermine.webservicesspring.service;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.TagManager;
import org.intermine.api.tag.TagTypes;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.FieldDescriptor;
import org.intermine.objectstore.ObjectStoreSummary;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.logic.WebCoreUtil;
import org.intermine.web.logic.config.FieldConfig;
import org.intermine.web.logic.config.FieldConfigHelper;
import org.intermine.web.logic.config.WebConfig;
import org.intermine.webservicesspring.model.Model;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ModelService extends WebService {

    @Autowired
    Model responseModel;

    private static final Logger LOG = Logger.getLogger(org.intermine.webservicesspring.service.ModelService.class);

    private WebConfig config = null;


    /**
     * Constructor.
     * @param im The API settings bundle
     */
    public ModelService(InterMineAPI im) {
        super(im);
        config = InterMineContext.getWebConfig();
    }


    public Model service(){
        final org.intermine.metadata.Model model = this.im.getModel();
        responseModel.setModel("Inside Set Model");
        //responseModel.setModel(Arrays.asList(new JSONObject(getAnnotatedModel(model)).toString()));
        return responseModel;
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
}

package org.licket.spring.resource;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.stream.Stream.concat;
import static org.licket.core.resource.javascript.JavascriptResource.JAVASCRIPT_MIMETYPE;
import static org.licket.spring.web.Contexts.CONTEXT_RESOURCES;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class ResourcesStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesStorage.class);

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private Collection<Resource> allResources;

    @Autowired
    private Collection<HeadParticipatingResource> headParticipatingResources;

    private List<Resource> dynamicResources = newArrayList();

    private Predicate<Resource> byName(String name) {
        return resource -> resource.getName().equals(name);
    }

    private Predicate<Resource> byMimetype(String mimetype) {
        return resource -> resource.getMimeType().equals(mimetype);
    }

    public boolean hasResource(String name, String mimetype) {
        return concat(allResources.stream(), dynamicResources.stream()).anyMatch(byName(name).and(byMimetype(mimetype)));
    }

    public void putResource(Resource resource) {
        if (hasResource(resource.getName(), resource.getMimeType())) {
            LOGGER.debug("Resource with name = {} and mimetype = {} is already there, skipping...", resource.getName(),
                resource.getMimeType());
            return;
        }
        dynamicResources.add(resource);
    }

    public Optional<Resource> getResource(String name) {
        return concat(allResources.stream(), dynamicResources.stream()).filter(byName(name)).findFirst();
    }

    public Optional<HeadParticipatingResource> getStylesheetResource(String name) {
        return headParticipatingResources.stream().filter(byName(name)).filter(byMimetype("text/css")).findFirst();
    }

    public Optional<HeadParticipatingResource> getJavascriptResource(String name) {
        return headParticipatingResources.stream().filter(byName(name)).filter(byMimetype(JAVASCRIPT_MIMETYPE))
            .findFirst();
    }

    public Stream<HeadParticipatingResource> getJavascriptResources() {
        return headParticipatingResources.stream().filter(byMimetype(JAVASCRIPT_MIMETYPE));
    }

    public String getResourceUrl(Resource resource) {
        return format("%s%s/%s", servletContext.getContextPath(), CONTEXT_RESOURCES, resource.getName());
    }
}

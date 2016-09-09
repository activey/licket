package org.licket.spring.resource;

import static java.lang.String.format;
import static org.licket.core.resource.javascript.JavascriptResource.JAVASCRIPT_MIMETYPE;
import static org.licket.spring.web.Contexts.CONTEXT_RESOURCES;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;

/**
 * @author activey
 */
public class ResourcesStorage {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private Collection<Resource> allResources;

    @Autowired
    private Collection<HeadParticipatingResource> headParticipatingResources;

    private Predicate<Resource> byName(String name) {
        return resource -> resource.getName().equals(name);
    }

    private Predicate<Resource> byMimetype(String mimetype) {
        return resource -> resource.getMimeType().equals(mimetype);
    }

    public Optional<Resource> getResource(String name) {
        return allResources.stream().filter(byName(name)).findFirst();
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

package org.licket.core.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.Resource;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author activey
 */
public interface ResourceStorage {
    boolean hasResource(String name, String mimetype);

    void putResource(Resource resource);

    Optional<Resource> getResource(String name);

    Optional<HeadParticipatingResource> getStylesheetResource(String name);

    Optional<HeadParticipatingResource> getJavascriptResource(String name);

    Stream<HeadParticipatingResource> getJavascriptResources();

    Stream<HeadParticipatingResource> getStylesheetResources();

    String getResourceUrl(Resource resource);
}

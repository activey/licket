package org.licket.core.resource;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author activey
 */
public interface ResourceStorage {
    boolean hasResource(String name, String mimetype);

    void putResource(Resource resource);

    Optional<Resource> getResource(String name);

    Stream<HeadParticipatingResource> getHeadJavascriptResources();

    Stream<FootParticipatingResource> getFootJavascriptResources();

    Stream<HeadParticipatingResource> getStylesheetResources();

    String getResourceUrl(Resource resource);

    void replaceResourceContent(Resource resource, byte[] newResourceContent);
}

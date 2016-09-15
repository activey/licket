package org.licket.core.view.render;

import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.Resource;
import org.licket.surface.element.SurfaceElement;

import java.util.function.Consumer;

/**
 * @author activey
 */
public interface ComponentRenderingContext {

    void renderResource(Resource resource);

    void onSurfaceElement(Consumer<SurfaceElement> element);
}

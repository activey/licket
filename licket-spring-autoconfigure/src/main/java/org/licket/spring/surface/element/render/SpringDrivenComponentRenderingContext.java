package org.licket.spring.surface.element.render;

import org.licket.core.resource.Resource;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.element.SurfaceElement;

import java.util.function.Consumer;

/**
 * @author grabslu
 */
public class SpringDrivenComponentRenderingContext implements ComponentRenderingContext {

    private SurfaceElement surfaceElement;
    private ResourcesStorage resourcesStorage;

    public SpringDrivenComponentRenderingContext(SurfaceElement surfaceElement, ResourcesStorage resourcesStorage) {
        this.surfaceElement = surfaceElement;
        this.resourcesStorage = resourcesStorage;
    }

    @Override
    public void renderResource(Resource resource) {
        resourcesStorage.putResource(resource);
    }

    @Override
    public void onSurfaceElement(Consumer<SurfaceElement> elementConsumer) {
        elementConsumer.accept(surfaceElement);
    }
}

package org.licket.core.view.render;

import org.licket.core.resource.Resource;
import org.licket.core.view.LicketComponent;
import org.licket.surface.element.SurfaceElement;

import java.io.InputStream;
import java.util.function.Consumer;

/**
 * @author activey
 */
public interface ComponentRenderingContext {

    void addResource(Resource resource);

    void compileComponentTemplateResource(LicketComponent<?> component);

    void onSurfaceElement(Consumer<SurfaceElement> element);
}

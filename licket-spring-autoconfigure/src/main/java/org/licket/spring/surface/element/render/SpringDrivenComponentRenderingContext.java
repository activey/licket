package org.licket.spring.surface.element.render;

import org.licket.core.id.CompositeId;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.Resource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.spring.surface.element.html.compiler.ComponentTemplateCompiler;
import org.licket.surface.SurfaceContext;
import org.licket.surface.element.SurfaceElement;
import org.licket.surface.tag.ElementFactories;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.function.Consumer;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/**
 * @author grabslu
 */
public class SpringDrivenComponentRenderingContext implements ComponentRenderingContext {

  @Autowired
  private ResourceStorage resourcesStorage;

  @Autowired
  private ElementFactories surfaceElementFactories;

  private final SurfaceElement surfaceElement;

  SpringDrivenComponentRenderingContext(SurfaceElement surfaceElement) {
    this.surfaceElement = surfaceElement;
  }

  @Override
  public void addResource(Resource resource) {
    resourcesStorage.putResource(resource);
  }

  @Override
  public void compileComponentTemplateResource(LicketComponent<?> component) {
    ComponentTemplateCompiler templateCompiler =
            new ComponentTemplateCompiler(() -> component);
    byte[] componentTemplate = templateCompiler.compile(surfaceContext(component.getCompositeId()));
    resourcesStorage.putResource(new ByteArrayResource(component.getCompositeId().getValue(), TEXT_HTML_VALUE, componentTemplate));
  }

  private SurfaceContext surfaceContext(CompositeId parentCompositeId) {
    return new SurfaceContext(surfaceElementFactories, parentCompositeId);
  }

  @Override
  public void onSurfaceElement(Consumer<SurfaceElement> elementConsumer) {
    elementConsumer.accept(surfaceElement);
  }
}

package org.licket.spring.surface.element.html;

import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.spring.surface.element.render.SpringDrivenComponentRenderingContext;
import org.licket.surface.SurfaceContext;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

/**
 * @author activey
 */
public class DefaultHtmlElement extends SurfaceElement {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHtmlElement.class);

  @Autowired
  private LicketApplication licketApplication;

  @Autowired
  private ResourceStorage resourcesStorage;

  public DefaultHtmlElement(String name) {
    super(name, HTML_NAMESPACE);
  }

  @Override
  protected final void onFinish(SurfaceContext surfaceContext) {
    if (!isComponentIdSet()) {
      return;
    }
    CompositeId compositeId = getComponentCompositeId();
    // when this SurfaceContext is dedicated for an external component template
    if (surfaceContext.isSubContext()) {
      compositeId = surfaceContext.getParentSurfaceContextRootId().join(compositeId);
    }
    Optional<LicketComponent<?>> componentOptional = licketApplication.findComponent(compositeId);
    if (!componentOptional.isPresent()) {
      LOGGER.warn("Unable to find component: {}", compositeId.getValue());
      return;
    }
    LicketComponent<?> component = componentOptional.get();
    component.render(new SpringDrivenComponentRenderingContext(this, resourcesStorage));
  }
}

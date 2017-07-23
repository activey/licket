package org.licket.spring.surface.element.render;

import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.spring.surface.element.html.DefaultHtmlElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author lukaszgrabski
 */
public final class ComponentRenderingContextFactory {

  @Autowired
  private AutowireCapableBeanFactory autowireCapableBeanFactory;

  public ComponentRenderingContext newRenderingContext(DefaultHtmlElement htmlElement) {
    SpringDrivenComponentRenderingContext renderingContext = new SpringDrivenComponentRenderingContext(htmlElement);
    autowireCapableBeanFactory.autowireBean(renderingContext);
    return renderingContext;
  }
}

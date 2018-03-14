package org.licket.spring.surface;

import org.licket.spring.surface.element.html.HtmlElementsConfiguration;
import org.licket.surface.tag.ElementFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author activey
 */
@Configuration
@ComponentScan(basePackageClasses = HtmlElementsConfiguration.class)
public class LicketSurfaceConfiguration {

  @Bean
  @RequestScope
  public ElementFactories elementFactories() {
    return new SpringElementFactories();
  }
}

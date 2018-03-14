package org.licket.spring;

import org.licket.core.DefaultLicketApplication;
import org.licket.core.LicketApplication;
import org.licket.core.view.mount.MountedComponents;
import org.licket.spring.decorator.LicketComponentDecoratorsConfiguration;
import org.licket.spring.mount.DefaultMountedComponents;
import org.licket.spring.resource.LicketResourcesConfiguration;
import org.licket.spring.surface.LicketSurfaceConfiguration;
import org.licket.spring.surface.element.render.ComponentRenderingContextFactory;
import org.licket.spring.web.LicketWebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@ConditionalOnClass(LicketApplication.class)
@Import({
        LicketResourcesConfiguration.class,
        LicketWebConfiguration.class,
        LicketSurfaceConfiguration.class,
        LicketComponentDecoratorsConfiguration.class
})
@PropertySource("classpath:/licket.properties")
public class LicketAutoconfigure {

  @Value("${application.vueName:default}")
  private String applicationName;

  @Bean
  @SessionScope
  public LicketApplication licketApplication() {
    return new DefaultLicketApplication(applicationName);
  }

  @Bean
  @SessionScope
  public MountedComponents mountedComponents() {
    return new DefaultMountedComponents();
  }

  @Bean
  public ComponentRenderingContextFactory renderingContextFactory() {
    return new ComponentRenderingContextFactory();
  }
}

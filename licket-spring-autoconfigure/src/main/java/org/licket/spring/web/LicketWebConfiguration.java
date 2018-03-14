package org.licket.spring.web;

import org.licket.core.module.VuePluginsConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({VuePluginsConfiguration.class})
@EnableCaching
public class LicketWebConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  @SessionScope
  public LicketRootController rootController() {
    return new LicketRootController();
  }

  @Bean
  @SessionScope
  public LicketFormController formControler() {
    return new LicketFormController();
  }

  @Bean
  @SessionScope
  public LicketActionLinkController actionLinkController() {
    return new LicketActionLinkController();
  }

  @Bean
  @SessionScope
  public LicketComponentController componentController() {
    return new LicketComponentController();
  }

  @Bean
  @SessionScope
  public LicketResourceController resourceController() {
    return new LicketResourceController();
  }

  @Bean
  @SessionScope
  public LicketDebugController debugController() {
    return new LicketDebugController();
  }

  @Bean
  public ErrorHandler errorHandler() {
    return new ErrorHandler();
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("/index");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
}

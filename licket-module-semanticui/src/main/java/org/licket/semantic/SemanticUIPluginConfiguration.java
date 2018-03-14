package org.licket.semantic;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.Resource;
import org.licket.core.resource.vue.VueLibraryResource;
import org.licket.core.view.hippo.vue.VuePlugin;
import org.licket.semantic.resource.JqueryLibraryResource;
import org.licket.semantic.resource.SemanticFonts1Resource;
import org.licket.semantic.resource.SemanticFonts2Resource;
import org.licket.semantic.resource.SemanticFonts3Resource;
import org.licket.semantic.resource.SemanticLibraryResource;
import org.licket.semantic.resource.SemanticStylesheetResource;
import org.licket.semantic.resource.SemanticUILibraryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class SemanticUIPluginConfiguration {

  @Bean
  public VuePlugin semanticPlugin() {
    return new SemanticUIPlugin();
  }

  @Bean
  public HeadParticipatingResource jqueryResource() {
    return new JqueryLibraryResource();
  }

  @Bean
  public HeadParticipatingResource semanticLibrary() {
    return new SemanticLibraryResource();
  }

  @Bean
  public Resource semanticLibraryResource(@Autowired VueLibraryResource vueLibraryResource) {
    return new SemanticUILibraryResource();
  }

  @Bean
  public HeadParticipatingResource semanticStylesheet() {
    return new SemanticStylesheetResource();
  }

  @Bean
  public Resource fonts1() {
    return new SemanticFonts1Resource();
  }

  @Bean
  public Resource fonts2() {
    return new SemanticFonts2Resource();
  }

  @Bean
  public Resource fonts3() {
    return new SemanticFonts3Resource();
  }
}

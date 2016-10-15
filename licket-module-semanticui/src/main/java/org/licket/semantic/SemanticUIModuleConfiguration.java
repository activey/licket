package org.licket.semantic;

import org.licket.core.resource.Resource;
import org.licket.core.view.hippo.ngmodule.AngularModule;
import org.licket.semantic.resource.SemanticUILibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class SemanticUIModuleConfiguration {

    @Bean
    public AngularModule semanticModule() {
        return new SemanticUIModule();
    }

    @Bean
    public Resource semanticLibraryResource() {
        return new SemanticUILibraryResource();
    }
}

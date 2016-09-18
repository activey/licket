package org.licket.spring.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.angular.AngularLibraryResource;
import org.licket.core.resource.angular.AngularPolyfillsLibraryResource;
import org.licket.core.resource.angular.RxLibraryResource;
import org.licket.core.resource.boot.AngularBootApplicationJavascriptResource;
import org.licket.core.resource.boot.AngularComponentsJavascriptResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.annotation.SessionScope;

/**
 * @author activey
 */
@Configuration
public class LicketResourcesConfiguration {

    @Bean
    @SessionScope
    public ResourcesStorage resourcesContainer() {
        return new ResourcesStorage();
    }

    @Bean
    @Order(1)
    public HeadParticipatingResource angularPolyfillsLibraryResource() {
        return new AngularPolyfillsLibraryResource();
    }

    @Bean
    @Order(2)
    public HeadParticipatingResource rxLibraryResource() {
        return new RxLibraryResource();
    }

    @Bean
    @Order(3)
    public HeadParticipatingResource angularLibraryResource() {
        return new AngularLibraryResource();
    }

    @Bean
    @Order(4)
    @SessionScope
    public HeadParticipatingResource angularComponentsResource() {
        return new AngularComponentsJavascriptResource();
    }

    @Bean
    @Order(5)
    @SessionScope
    public HeadParticipatingResource angularApplicationBootResource() {
        return new AngularBootApplicationJavascriptResource();
    }
}

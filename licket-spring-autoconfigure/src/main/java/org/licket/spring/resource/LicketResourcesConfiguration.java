package org.licket.spring.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.angular.AngularLibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public HeadParticipatingResource angularLibraryResource() {
        return new AngularLibraryResource();
    }
}

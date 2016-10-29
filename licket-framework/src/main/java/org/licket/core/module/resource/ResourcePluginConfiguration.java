package org.licket.core.module.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class ResourcePluginConfiguration {

    @Bean
    public HeadParticipatingResource resourcePluginResource() {
        return new ResourcePluginResource();
    }

    @Bean
    public HttpCommunicationService httpCommunicationService() {
        return new HttpCommunicationService();
    }
}

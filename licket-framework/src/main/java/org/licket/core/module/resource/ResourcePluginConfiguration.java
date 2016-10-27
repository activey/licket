package org.licket.core.module.resource;

import org.licket.core.module.resource.resource.ResourcePluginResource;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.hippo.angular.ngmodule.VuePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class ResourcePluginConfiguration {

    @Bean
    public VuePlugin resourcePlugin() {
        return new ResourcePlugin();
    }

    @Bean
    public HeadParticipatingResource resourcePluginResource() {
        return new ResourcePluginResource();
    }

    @Bean
    public HttpCommunicationService httpCommunicationService() {
        return new HttpCommunicationService();
    }
}

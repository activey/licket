package org.licket.core.module.application;

import org.licket.core.module.application.resource.ApplicationEventHubResource;
import org.licket.core.module.application.resource.ApplicationModulePluginResource;
import org.licket.core.module.application.security.LicketComponentSecurity;
import org.licket.core.module.resource.HttpCommunicationService;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.hippo.vue.VuePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author activey
 */
@Configuration
public class ApplicationModulePluginConfiguration {

    @Bean
    public VuePlugin applicationModulePlugin(@Autowired HttpCommunicationService httpCommunicationService) {
        return new ApplicationModulePlugin(
                communicationService(httpCommunicationService),
                modelReloader(),
                componentSecurity()
        );
    }

    @Bean
    public LicketRemote communicationService(@Autowired HttpCommunicationService httpService) {
        return new LicketRemote(httpService);
    }

    @Bean
    public LicketComponentModelReloader modelReloader() {
        return new LicketComponentModelReloader();
    }

    @Bean
    public LicketComponentSecurity componentSecurity() {
        return new LicketComponentSecurity();
    }

    @Bean
    @Order(1)
    public HeadParticipatingResource applicationEventHubResource() {
        return new ApplicationEventHubResource();
    }

    @Bean
    @Order(2)
    public HeadParticipatingResource applicationModulePluginResource() {
        return new ApplicationModulePluginResource();
    }
}

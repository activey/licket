package org.licket.core.module.application;

import org.licket.core.module.application.resource.ApplicationModulePluginResource;
import org.licket.core.module.resource.HttpCommunicationService;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.hippo.angular.ngmodule.VuePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class ApplicationModuleConfiguration {

    @Bean
    public VuePlugin applicationModulePlugin(@Autowired HttpCommunicationService httpCommunicationService) {
        return new ApplicationModulePlugin(communicationService(httpCommunicationService), modelReloader());
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
    public HeadParticipatingResource applicationModulePluginResource() {
        return new ApplicationModulePluginResource();
    }
}

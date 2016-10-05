package org.licket.core.module.application;

import org.licket.core.module.http.HttpCommunicationService;
import org.licket.core.view.hippo.ngmodule.AngularModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class ApplicationModuleConfiguration {

    @Bean(name = "applicationModule")
    public AngularModule applicationModule(@Autowired @Qualifier("httpCommunicationService") HttpCommunicationService httpService) {
        return new ApplicationModule(communicationService(httpService));
    }

    @Bean
    public LicketRemoteCommunication communicationService(HttpCommunicationService httpService) {
        return new LicketRemoteCommunication(httpService);
    }
}

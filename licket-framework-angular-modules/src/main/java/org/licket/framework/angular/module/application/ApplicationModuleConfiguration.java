package org.licket.framework.angular.module.application;

import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
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
    public AngularModule applicationModule(@Autowired @Qualifier("communicationService") AngularClass communicationService) {
        return new ApplicationModule(communicationService);
    }

    @Bean(name = "communicationService")
    public AngularClass httpCommunicationService() {
        return new LicketRemoteCommunication();
    }
}

package org.licket.framework.angular.module.http;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class HttpModuleConfiguration {

    @Bean
    public AngularModule httpModule(@Autowired @Qualifier("httpCommunicationService") AngularInjectable httpCommunicationService) {
        return new HttpModule(httpCommunicationService);
    }

    @Bean(name = "httpCommunicationService")
    public AngularInjectable httpCommunicationService() {
        return new HttpCommunicationService();
    }
}

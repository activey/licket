package org.licket.framework.angular.module.platform;

import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class BrowserModuleConfiguration {

    @Bean
    public AngularModule browserModule() {
        return new BrowserModule();
    }
}

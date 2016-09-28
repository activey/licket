package org.licket.framework.angular.module.forms;

import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.angular.module.platform.BrowserModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class FormsModuleConfiguration {

    @Bean
    public AngularModule formsModule() {
        return new FormsModule();
    }
}

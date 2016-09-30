package org.licket.core.module.forms;

import org.licket.core.view.hippo.ngmodule.AngularModule;
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

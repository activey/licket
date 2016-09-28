package org.licket.framework.angular.module.application;

import org.licket.core.LicketApplication;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author activey
 */
@Configuration
public class ApplicationModuleConfiguration {

    @Bean(name = "applicationModule")
    public AngularModule applicationModule(@Autowired LicketApplication application) {
        return new ApplicationModule();
    }
}

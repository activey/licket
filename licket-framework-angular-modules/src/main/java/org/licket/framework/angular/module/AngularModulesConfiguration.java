package org.licket.framework.angular.module;

import org.licket.framework.angular.module.application.ApplicationModuleConfiguration;
import org.licket.framework.angular.module.forms.FormsModuleConfiguration;
import org.licket.framework.angular.module.http.HttpModuleConfiguration;
import org.licket.framework.angular.module.platform.BrowserModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author activey
 */
@Configuration
@Import({
        BrowserModuleConfiguration.class,
        FormsModuleConfiguration.class,
        HttpModuleConfiguration.class,
        ApplicationModuleConfiguration.class
})
public class AngularModulesConfiguration {}

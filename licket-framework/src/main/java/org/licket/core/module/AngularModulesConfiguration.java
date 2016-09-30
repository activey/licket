package org.licket.core.module;

import org.licket.core.module.application.ApplicationModuleConfiguration;
import org.licket.core.module.forms.FormsModuleConfiguration;
import org.licket.core.module.http.HttpModuleConfiguration;
import org.licket.core.module.platform.BrowserModuleConfiguration;
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

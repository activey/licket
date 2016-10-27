package org.licket.core.module;

import org.licket.core.module.application.ApplicationModuleConfiguration;
import org.licket.core.module.resource.ResourcePluginConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author activey
 */
@Configuration
@Import({
        ResourcePluginConfiguration.class,
        ApplicationModuleConfiguration.class
})
public class AngularModulesConfiguration {}

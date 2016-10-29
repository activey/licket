package org.licket.core.module;

import org.licket.core.module.application.ApplicationModulePluginConfiguration;
import org.licket.core.module.resource.ResourcePluginConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author activey
 */
@Configuration
@Import({
        ResourcePluginConfiguration.class,
        ApplicationModulePluginConfiguration.class
})
public class VuePluginsConfiguration {}

package org.licket.spring;

import org.licket.core.LicketApplicationBuilder;
import org.licket.core.resource.Resource;
import org.licket.spring.factory.LicketApplicationFactory;
import org.licket.spring.resource.LicketResourcesConfiguration;
import org.licket.spring.surface.LicketSurfaceConfiguration;
import org.licket.spring.web.LicketWebConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.licket.core.LicketApplicationBuilder.applicationBuilder;

@Configuration
@ConditionalOnClass(Resource.class)
@Import({
        LicketWebConfiguration.class,
        LicketResourcesConfiguration.class,
        LicketSurfaceConfiguration.class
})
public class LicketAutoconfigure {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketAutoconfigure.class);

    @Bean
    @ConditionalOnMissingBean
    public LicketApplicationBuilder licketApplicationBuilder() {
        LOGGER.warn("No Licket application bean found, creating default one.");
        return applicationBuilder();
    }

    @Bean
    public LicketApplicationFactory applicationFactory(LicketApplicationBuilder builder, AutowireCapableBeanFactory beanFactory) {
        return new LicketApplicationFactory(builder, beanFactory);
    }
}

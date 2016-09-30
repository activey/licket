package org.licket.spring;

import org.licket.core.DefaultLicketApplication;
import org.licket.core.LicketApplication;
import org.licket.spring.resource.LicketResourcesConfiguration;
import org.licket.spring.surface.LicketSurfaceConfiguration;
import org.licket.spring.web.LicketWebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@ConditionalOnClass(LicketApplication.class)
@Import({
        LicketWebConfiguration.class,
        LicketResourcesConfiguration.class,
        LicketSurfaceConfiguration.class
})
@PropertySource("classpath:/licket.properties")
public class LicketAutoconfigure {

    @Value("${application.angularName:default}")
    private String applicationName;

    @Bean
    @SessionScope
    public LicketApplication licketApplication() {
        return new DefaultLicketApplication(applicationName);
    }
}

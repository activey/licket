package org.licket.spring.web;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@EnableCaching
public class LicketWebConfiguration {

    @Bean
    @SessionScope
    public LicketComponentController componentController() {
        return new LicketComponentController();
    }

    @Bean
    @SessionScope
    public LicketResourceController resourceController() {
        return new LicketResourceController();
    }

}

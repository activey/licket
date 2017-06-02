package org.licket.demo;

import org.licket.demo.licket.LicketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LicketConfiguration.class)
public class DemoSpringBootApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBootApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoSpringBootApp.class);
    }
}

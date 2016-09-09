package org.licket.demo;

import org.licket.demo.licket.LicketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LicketConfiguration.class)
public class DemoSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBootApp.class, args);
    }
}

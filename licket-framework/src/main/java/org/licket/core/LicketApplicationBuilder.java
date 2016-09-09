package org.licket.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author activey
 */
public class LicketApplicationBuilder {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private LicketApplication licketApplication;

    private LicketApplicationBuilder() {
        licketApplication = new LicketApplication();
    }

    public static LicketApplicationBuilder applicationBuilder() {
        return new LicketApplicationBuilder();
    }

    public LicketApplicationBuilder name(String applicationName) {
        licketApplication.setName(applicationName);
        return this;
    }

    public LicketApplication build() {
        beanFactory.autowireBean(licketApplication);
        return licketApplication;
    }
}

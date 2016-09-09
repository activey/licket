package org.licket.spring.factory;

import org.licket.core.LicketApplication;
import org.licket.core.LicketApplicationBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

/**
 * @author activey
 */
@Service
public class LicketApplicationFactory implements FactoryBean<LicketApplication> {

    private LicketApplicationBuilder licketApplicationBuilder;

    public LicketApplicationFactory(LicketApplicationBuilder licketApplicationBuilder, AutowireCapableBeanFactory beanFactory) {
        this.licketApplicationBuilder = licketApplicationBuilder;
    }

    @Override
    public LicketApplication getObject() throws Exception {
        return licketApplicationBuilder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return LicketApplication.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

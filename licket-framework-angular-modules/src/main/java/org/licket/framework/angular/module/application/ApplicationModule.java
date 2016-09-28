package org.licket.framework.angular.module.application;

import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.hippo.PropertyNameBuilder;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ApplicationModule implements AngularModule, AngularClass {

    @Override
    public PropertyNameBuilder angularName() {
        return property(name("app"), name("AppModule"));
    }

    @Override
    public Iterable<AngularInjectable> getInjectables() {
        return newArrayList();
    }
}

package org.licket.framework.angular.module.application;

import org.licket.core.LicketApplication;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ApplicationModule implements AngularModule, AngularClass {

    private final List<AngularInjectable> injectables;

    @Autowired
    private LicketApplication application;

    public ApplicationModule(AngularInjectable... injectables) {
        this.injectables = asList(injectables);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(name("app"), name("AppModule"));
    }

    @Override
    public Iterable<AngularInjectable> getInjectables() {
        List<AngularInjectable> injectables = newArrayList(this.injectables);
        // TODO make up decision how to define list of application module injectables, should all the licket components be there?
        application.traverseDownContainers(container -> {
            if (!container.getComponentContainerView().isExternalized()) {
                return false;
            }
            injectables.add(container);
            return true;
        });
        return injectables;
    }
}

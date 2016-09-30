package org.licket.core.module.application;

import org.licket.core.LicketApplication;
import org.licket.core.view.hippo.ngclass.AngularClass;
import org.licket.core.view.hippo.ngclass.AngularInjectable;
import org.licket.core.view.hippo.ngmodule.AngularModule;
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
    private final List<AngularClass> classes = newArrayList();

    @Autowired
    private LicketApplication application;

    public ApplicationModule(AngularClass communicationService, AngularInjectable... injectables) {
        this.injectables = asList(injectables);
        this.classes.add(communicationService);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(name("app"), name("AppModule"));
    }

    @Override
    public Iterable<AngularInjectable> injectables() {
        return newArrayList(this.injectables);
    }

    @Override
    public Iterable<AngularClass> classes() {
        List<AngularClass> classes = newArrayList(this.classes);
        application.traverseDown(component -> {
            if (!component.getView().isExternalized()) {
                return false;
            }
            classes.add(0, component);
            return true;
        });
        return classes;
    }
}

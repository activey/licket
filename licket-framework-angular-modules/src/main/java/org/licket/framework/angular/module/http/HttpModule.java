package org.licket.framework.angular.module.http;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class HttpModule implements AngularModule {

    private final List<AngularInjectable> injectables;

    public HttpModule(AngularInjectable... injectables) {
        this.injectables = Arrays.asList(injectables);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(NameBuilder.name("ng"), NameBuilder.name("http")), NameBuilder.name("HttpModule"));
    }

    @Override
    public Iterable<AngularInjectable> getInjectables() {
        return injectables;
    }
}

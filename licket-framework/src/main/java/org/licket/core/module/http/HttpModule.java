package org.licket.core.module.http;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.hippo.testing.ngmodule.AbstractAngularModule;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class HttpModule extends AbstractAngularModule {

    public HttpModule(AngularInjectable... injectables) {
        super(injectables);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(name("ng"), name("http")), name("HttpModule"));
    }
}

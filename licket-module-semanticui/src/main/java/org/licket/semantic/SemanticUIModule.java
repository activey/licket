package org.licket.semantic;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

import org.licket.core.view.hippo.ngclass.AngularInjectable;
import org.licket.core.view.hippo.ngmodule.AbstractAngularModule;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author activey
 */
public class SemanticUIModule extends AbstractAngularModule {

    public SemanticUIModule(AngularInjectable... injectables) {
        super(injectables);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(name("ng"), name("http")), name("HttpModule"));
    }
}

package org.licket.core.view.hippo.testing.ngmodule;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.framework.hippo.PropertyNameBuilder;

import java.util.List;

/**
 * @author activey
 */
public abstract class AbstractAngularModule implements AngularModule {

    private List<AngularInjectable> injectables;

    public AbstractAngularModule(List<AngularInjectable> injectables) {
        this.injectables = injectables;
    }

    @Override
    public PropertyNameBuilder angularName() {
        return null;
    }

    @Override
    public Iterable<AngularInjectable> getInjectables() {
        return injectables;
    }
}

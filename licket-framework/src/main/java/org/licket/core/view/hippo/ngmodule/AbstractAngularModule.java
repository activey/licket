package org.licket.core.view.hippo.ngmodule;

import org.licket.core.view.hippo.ngclass.AngularInjectable;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author activey
 */
public abstract class AbstractAngularModule implements AngularModule {

    private final List<AngularInjectable> injectables;

    public AbstractAngularModule(AngularInjectable... injectables) {
        this.injectables = asList(injectables);
    }

    @Override
    public final Iterable<AngularInjectable> injectables() {
        return injectables;
    }
}

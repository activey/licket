package org.licket.core.view.hippo.testing.ngmodule;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.framework.hippo.PropertyNameBuilder;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public interface AngularModule {

    PropertyNameBuilder angularName();

    default Iterable<AngularInjectable> getInjectables() {
        return newArrayList();
    }
}

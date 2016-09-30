package org.licket.core.view.hippo.ngmodule;

import org.licket.core.view.hippo.ngclass.AngularClass;
import org.licket.core.view.hippo.ngclass.AngularInjectable;
import org.licket.framework.hippo.PropertyNameBuilder;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public interface AngularModule {

    PropertyNameBuilder angularName();

    default Iterable<AngularInjectable> injectables() {
        return newArrayList();
    }

    default Iterable<AngularClass> classes() {
        return newArrayList();
    }

}

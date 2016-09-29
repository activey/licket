package org.licket.core.view.hippo.testing.ngmodule;

import com.google.common.collect.Iterables;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
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

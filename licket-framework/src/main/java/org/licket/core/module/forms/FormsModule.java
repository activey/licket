package org.licket.core.module.forms;

import org.licket.core.view.hippo.ngmodule.AngularModule;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class FormsModule implements AngularModule {

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(NameBuilder.name("ng"), NameBuilder.name("forms")), NameBuilder.name("FormsModule"));
    }
}

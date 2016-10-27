package org.licket.core.module.resource;

import org.licket.core.view.hippo.angular.ngmodule.VuePlugin;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ResourcePlugin implements VuePlugin {

    @Override
    public PropertyNameBuilder vueName() {
        return property("", "");
    }
}

package org.licket.core.module.application;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.view.hippo.angular.ngmodule.VuePlugin;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author activey
 */
public class ApplicationModulePlugin implements VuePlugin {

    private VueClass[] moduleServices;

    public ApplicationModulePlugin(VueClass... moduleServices) {
        this.moduleServices = moduleServices;
    }

    @Override
    public PropertyNameBuilder vueName() {
        return property(name("app"), name("AppModule"));
    }

}

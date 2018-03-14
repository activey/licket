package org.licket.core.module.application;

import org.licket.core.view.hippo.vue.VuePlugin;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Consumer;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ApplicationModulePlugin implements VuePlugin {

  @Autowired
  private List<ApplicationModuleService> moduleServices;

  @Override
  public PropertyNameBuilder vueName() {
    return property(name("app"), name("AppModule"));
  }

  public void forEachService(Consumer<ApplicationModuleService> serviceConsumer) {
    moduleServices.stream().forEach(serviceConsumer);
  }
}

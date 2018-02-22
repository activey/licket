package org.licket.core.view.hippo;

import org.licket.framework.hippo.AbstractAstNodeBuilder;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public interface ComponentCallTargetOrigin {

  AbstractAstNodeBuilder<?> buildTargetOrigin();

  static ComponentCallTargetOrigin fromAppInstance() {
    return () -> property("app", "instance");
  }

  static ComponentCallTargetOrigin fromVm() {
    return () -> name("vm");
  }
}

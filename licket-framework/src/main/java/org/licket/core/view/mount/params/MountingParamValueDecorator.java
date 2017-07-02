package org.licket.core.view.mount.params;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public interface MountingParamValueDecorator {

  static MountingParamValueDecorator fromParentModel(String parentModelPropertyName) {
    return (propertyBuilder) -> propertyBuilder.value(property(property(property("this", "$parent"), "model"), parentModelPropertyName));
  }

  static MountingParamValueDecorator simple(String value) {
    return (propertyBuilder) -> propertyBuilder.value(name(value));
  }

  AbstractAstNodeBuilder<?> decorateProperty(ObjectPropertyBuilder propertyBuilder);
}

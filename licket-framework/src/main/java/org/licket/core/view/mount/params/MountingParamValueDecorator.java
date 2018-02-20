package org.licket.core.view.mount.params;

import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;

import static org.licket.framework.hippo.NameBuilder.name;

/**
 * @author lukaszgrabski
 */
public interface MountingParamValueDecorator {

  static MountingParamValueDecorator fromParentModelProperty(String parentModelPropertyName) {
    return (propertyBuilder) -> propertyBuilder.value(ComponentModelProperty.fromParentModelProperty(parentModelPropertyName).builder());
  }

  static MountingParamValueDecorator simple(String value) {
    return (propertyBuilder) -> propertyBuilder.value(name(value));
  }

  AbstractAstNodeBuilder<?> decorateProperty(ObjectPropertyBuilder propertyBuilder);
}

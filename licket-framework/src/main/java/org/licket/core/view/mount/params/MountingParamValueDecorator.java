package org.licket.core.view.mount.params;

import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;

/**
 * @author lukaszgrabski
 */
public interface MountingParamValueDecorator {

  static MountingParamValueDecorator fromParentModelProperty(String parentModelPropertyName) {
    return (propertyBuilder) -> propertyBuilder.value(ComponentModelProperty.fromComponentParentModelProperty(parentModelPropertyName).builder());
  }

  AbstractAstNodeBuilder<?> decorateProperty(ObjectPropertyBuilder propertyBuilder);
}

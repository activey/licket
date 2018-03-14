package org.licket.core.view.hippo;

import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public interface ComponentModelProperty {

  static ComponentModelProperty fromComponentParentModelProperty(String parentModelPropertyName) {
    return () -> property(property(property("this", "$parent"), "model"), parentModelPropertyName);
  }

  static ComponentModelProperty fromComponentModelProperty(String modelPropertyName) {
    return () -> property(property("this", "model"), modelPropertyName);
  }

  PropertyNameBuilder builder();
}

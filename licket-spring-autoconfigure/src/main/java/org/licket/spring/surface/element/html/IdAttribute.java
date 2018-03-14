package org.licket.spring.surface.element.html;

import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.SurfaceElement;

/**
 * @author activey
 */
public class IdAttribute extends BaseAttribute {

  public IdAttribute(String name) {
    super(name, "");
  }

  @Override
  protected void onStart(SurfaceElement relatedElement) {
    relatedElement.setComponentId(getValue());
  }
}

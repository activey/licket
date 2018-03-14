package org.licket.surface.tag;

import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.SurfaceElement;

import java.util.Optional;

/**
 * @author activey
 */
public interface ElementFactory {

  SurfaceElement createDefaultElement(String name);

  Optional<SurfaceElement> createElement(String name);

  Optional<BaseAttribute> createAttribute(String name);

  boolean matchesNamespace(String namespace);
}

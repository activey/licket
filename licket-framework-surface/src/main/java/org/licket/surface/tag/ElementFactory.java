package org.licket.surface.tag;

import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.BaseElement;

import java.util.Optional;

/**
 * @author activey
 */
public interface ElementFactory {

    Optional<BaseElement> createElement(String name);

    Optional<BaseAttribute> createAttribute(String name);

    boolean matchesNamespace(String namespace);
}

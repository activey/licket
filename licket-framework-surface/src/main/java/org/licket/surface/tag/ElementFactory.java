package org.licket.surface.tag;

import org.licket.surface.element.BaseElement;

import java.util.Optional;

/**
 * @author activey
 */
public interface ElementFactory {
    Optional<BaseElement> createElement(String name);

    boolean matchesNamespace(String namespace);
}

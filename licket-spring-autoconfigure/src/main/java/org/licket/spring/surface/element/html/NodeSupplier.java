package org.licket.spring.surface.element.html;

import nu.xom.Node;

/**
 * @author activey
 */
public interface NodeSupplier<T extends Node> {

    T get(String name);
}

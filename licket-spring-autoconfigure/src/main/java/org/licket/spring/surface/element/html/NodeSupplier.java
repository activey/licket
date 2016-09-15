package org.licket.spring.surface.element.html;

import org.licket.xml.dom.Node;

/**
 * @author activey
 */
public interface NodeSupplier<T extends Node> {

    T get(String name);
}

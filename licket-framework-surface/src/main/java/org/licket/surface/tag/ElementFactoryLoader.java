package org.licket.surface.tag;

import java.util.ServiceLoader;

/**
 * @author activey
 */
public class ElementFactoryLoader {

    public static ServiceLoader<ElementFactory> loader() {
        return ServiceLoader.load(ElementFactory.class);
    }
}

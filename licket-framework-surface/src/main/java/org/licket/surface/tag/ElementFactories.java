package org.licket.surface.tag;

import org.licket.surface.tag.factories.ServiceLoaderElementFactories;

import java.util.Optional;

/**
 * @author activey
 */
public interface ElementFactories {

  static ElementFactories serviceLoaderFactories() {
    return new ServiceLoaderElementFactories();
  }

  Optional<ElementFactory> getElementFactoryByNamespace(String namespace);
}

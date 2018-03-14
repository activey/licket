package org.licket.surface.tag.factories;

import org.licket.surface.tag.ElementFactories;
import org.licket.surface.tag.ElementFactory;

import java.util.Optional;
import java.util.ServiceLoader;

import static java.util.stream.StreamSupport.stream;
import static org.licket.surface.tag.ElementFactoryLoader.loader;

/**
 * @author activey
 */
public class ServiceLoaderElementFactories implements ElementFactories {

  private ServiceLoader<ElementFactory> loader;

  public ServiceLoaderElementFactories() {
    loadFactories();
  }

  private void loadFactories() {
    loader = loader();
  }

  @Override
  public Optional<ElementFactory> getElementFactoryByNamespace(String namespace) {
    return stream(loader.spliterator(), true).filter(factory -> factory.matchesNamespace(namespace)).findFirst();
  }
}

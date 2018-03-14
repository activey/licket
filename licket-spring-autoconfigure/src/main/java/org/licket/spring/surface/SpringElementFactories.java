package org.licket.spring.surface;

import org.licket.surface.tag.ElementFactories;
import org.licket.surface.tag.ElementFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

/**
 * @author activey
 */
public class SpringElementFactories implements ElementFactories {

  @Autowired
  private Collection<ElementFactory> elementFactories;

  @Override
  public Optional<ElementFactory> getElementFactoryByNamespace(String namespace) {
    return elementFactories.stream().filter(factory -> factory.matchesNamespace(namespace)).findFirst();
  }
}

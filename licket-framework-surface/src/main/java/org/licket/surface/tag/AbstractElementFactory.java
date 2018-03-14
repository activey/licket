package org.licket.surface.tag;

import org.licket.surface.attribute.AttributeProvider;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.ElementProvider;
import org.licket.surface.element.SurfaceElement;

import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Optional.ofNullable;
import static org.licket.surface.element.ElementProvider.empty;

/**
 * @author activey
 */
public abstract class AbstractElementFactory implements ElementFactory {

  // TODO make it prettier ...
  private Map<String, ElementProvider> elements = newHashMap();
  private Map<String, AttributeProvider> attributes = newHashMap();
  private String namespace;

  public AbstractElementFactory(String namespace) {
    this.namespace = checkNotNull(namespace);
  }

  @Override
  public final Optional<SurfaceElement> createElement(String name) {
    return ofNullable(ofNullable(elements.get(name)).orElse(empty(name)).provideElement());
  }

  public final Optional<BaseAttribute> createAttribute(String name) {
    return ofNullable(ofNullable(attributes.get(name)).orElse(AttributeProvider.empty(name)).provideAttribute());
  }

  public void element(ElementProvider elementProvider) {
    elements.put(elementProvider.getLocalName(), elementProvider);
  }

  public void attribute(AttributeProvider attributeProvider) {
    attributes.put(attributeProvider.getLocalName(), attributeProvider);
  }

  @Override
  public final boolean matchesNamespace(String namespace) {
    return this.namespace.equals(namespace);
  }
}

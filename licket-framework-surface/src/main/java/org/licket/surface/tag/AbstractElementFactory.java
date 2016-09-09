package org.licket.surface.tag;

import org.licket.surface.element.BaseElement;

import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Optional.ofNullable;

/**
 * @author activey
 */
public abstract class AbstractElementFactory implements ElementFactory {

    private Map<String, BaseElement> elements = newHashMap();
    private String namespace;

    public AbstractElementFactory(String namespace) {
        this.namespace = checkNotNull(namespace);
    }

    @Override
    public final Optional<BaseElement> createElement(String name) {
        return ofNullable(elements.get(name));
    }

    protected void element(BaseElement element) {
        elements.put(element.getLocalName(), element);
    }

    @Override
    public final boolean matchesNamespace(String namespace) {
        return this.namespace.equals(namespace);
    }
}

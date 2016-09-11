package org.licket.surface;

import java.util.Optional;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.BaseElement;
import org.licket.surface.tag.ElementFactories;
import org.licket.surface.tag.ElementFactory;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.NodeFactory;
import nu.xom.Nodes;
import nu.xom.Attribute.Type;

public class SurfaceNodeFactory extends NodeFactory {

    private final SurfaceContext surfaceContext;
    private ElementFactories elementFactories;
    private BaseElement currentElement;

    public SurfaceNodeFactory(SurfaceContext surfaceContext, ElementFactories elementFactories) {
        this.surfaceContext = surfaceContext;
        this.elementFactories = elementFactories;
    }

    @Override
    public Element makeRootElement(String name, String namespace) {
        return startMakingElement(name, namespace);
    }

    @Override
    public Element startMakingElement(String name, String namespace) {
        Optional<ElementFactory> elementFactoryOptional = elementFactories.getElementFactoryByNamespace(namespace);
        if (!elementFactoryOptional.isPresent()) {
            currentElement = new BaseElement(name, namespace);
        }
        Optional<BaseElement> elementOptional = elementFactoryOptional.get().createElement(name);
        if (elementOptional.isPresent()) {
            currentElement = elementOptional.get();
        } else {
            currentElement = new BaseElement(name, namespace);
        }
        currentElement.start();
        return currentElement;
    }

    @Override
    public Nodes makeComment(String data) {
        if (surfaceContext.isSkipComments()) {
            return new Nodes();
        }
        return super.makeComment(data);
    }

    @Override
    public Nodes makeDocType(String rootElementName, String publicID, String systemID) {
        if (surfaceContext.isSkipComments()) {
            return new Nodes();
        }
        return super.makeDocType(rootElementName, publicID, systemID);
    }

    @Override
    public Nodes makeAttribute(String name, String namespace, String value, Type type) {
        Optional<ElementFactory> elementFactoryOptional = elementFactories.getElementFactoryByNamespace(namespace);
        if (!elementFactoryOptional.isPresent()) {
            return new Nodes(new Attribute(name, namespace, value));
        }
        Attribute xmlAttribute = new Attribute(name, namespace, value);
        Optional<BaseAttribute> attributeOptional = elementFactoryOptional.get()
            .createAttribute(xmlAttribute.getLocalName());
        if (attributeOptional.isPresent()) {
            BaseAttribute attribute = attributeOptional.get();
            attribute.setValue(value);
            attribute.start(currentElement);

            return new Nodes(attribute);
        }
        return new Nodes(xmlAttribute);
    }

    @Override
    public Nodes finishMakingElement(Element element) {
        BaseElement finishingElement = (BaseElement) element;
        finishingElement.finish();

        return new Nodes(finishingElement);
    }
}

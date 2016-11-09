package org.licket.surface;

import java.util.Optional;
import org.licket.surface.attribute.BaseAttribute;
import org.licket.surface.element.SurfaceElement;
import org.licket.surface.tag.ElementFactories;
import org.licket.surface.tag.ElementFactory;
import org.licket.xml.dom.Attribute;
import org.licket.xml.dom.Attribute.Type;
import org.licket.xml.dom.Element;
import org.licket.xml.dom.NodeFactory;
import org.licket.xml.dom.Nodes;

public class SurfaceNodeFactory extends NodeFactory {

    private final SurfaceContext surfaceContext;
    private ElementFactories elementFactories;
    private SurfaceElement currentElement;

    public SurfaceNodeFactory(SurfaceContext surfaceContext, ElementFactories elementFactories) {
        this.surfaceContext = surfaceContext;
        this.elementFactories = elementFactories;
    }

    @Override
    public Element startMakingElement(String localName, String namespace) {
        Optional<ElementFactory> elementFactoryOptional = elementFactories.getElementFactoryByNamespace(namespace);
        if (!elementFactoryOptional.isPresent()) {
            currentElement = new SurfaceElement(localName, namespace);
            currentElement.start();
            return currentElement;
        }
        Optional<SurfaceElement> elementOptional = elementFactoryOptional.get().createElement(localName);
        if (elementOptional.isPresent()) {
            currentElement = elementOptional.get();
        } else {
            currentElement = elementFactoryOptional.get().createDefaultElement(localName);
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
    public Attribute makeAttribute(String name, String namespace, String value, Type type) {
        Optional<ElementFactory> elementFactoryOptional = elementFactories.getElementFactoryByNamespace(namespace);
        Attribute xmlAttribute = new Attribute(name, namespace, value);
        if (!elementFactoryOptional.isPresent()) {
            return xmlAttribute;
        }
        Optional<BaseAttribute> attributeOptional = elementFactoryOptional.get()
            .createAttribute(xmlAttribute.getLocalName());
        if (attributeOptional.isPresent()) {
            BaseAttribute attribute = attributeOptional.get();
            attribute.setValue(value);
            attribute.start(currentElement);

            return attribute;
        }
        return xmlAttribute;
    }

    @Override
    public Element finishMakingElement(Element element) {
        SurfaceElement finishingElement = (SurfaceElement) element;
        finishingElement.finish(surfaceContext);
        return finishingElement;
    }
}

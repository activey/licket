package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author grabslu
 */
public class Element extends Node {

    private List<Attribute> attributes = new LinkedList<>();
    private List<Element> children = new LinkedList<>();

    public Element(String localName, String namespace) {
        this("", localName, namespace);
    }

    public Element(String prefix, String localName, String namespace) {
        super(prefix, localName, namespace);
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void appendChildElement(Element childElement) {
        childElement.setParent(this);
        children.add(childElement);
    }

    public void replaceChild(Element element, Element replacement) {
        for (int childIndex = 0; childIndex < children.size(); childIndex++) {
            if (children.get(childIndex).equals(element)) {
                children.set(childIndex, replacement);
            }
        }
    }

    public void detach() {
        getParent().removeChild(this);
    }

    private void removeChild(Element childElement) {
        for (int childIndex = 0; childIndex < children.size(); childIndex++) {
            if (children.get(childIndex).equals(childElement)) {
                children.remove(childIndex);
            }
        }
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(getPrefix(), getLocalName(), getNamespace());
        for (Attribute attribute : attributes) {
            attribute.toXML(writer);
        }
        for (Element element : children) {
            element.toXML(writer);
        }
        writer.writeEndElement();
    }

    public void removeChildren() {
        children.clear();
    }
}
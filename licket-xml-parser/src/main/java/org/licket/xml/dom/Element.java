package org.licket.xml.dom;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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

    public void setAttribute(String name, String value) {
        Attribute newAttribute = new Attribute(name, getNamespace());
        newAttribute.setValue(value);
        addAttribute(newAttribute);
    }

    public Iterable<Element> children() {
        return children;
    }

    public void appendChildElement(Element childElement) {
        childElement.setParent(this);
        children.add(childElement);
    }

    public void replaceChild(Element element, Element replacement) {
        for (int childIndex = 0; childIndex < children.size(); childIndex++) {
            if (children.get(childIndex).equals(element)) {
                children.set(childIndex, replacement);
                replacement.setParent(this);
            }
        }
    }

    public final void replaceWith(Element replacement) {
        Element parentElement = getParent();
        if (parentElement == null) {
            return;
        }
        parentElement.replaceChild(this, replacement);
    }

    public Element detach() {
        return getParent().removeChild(this);
    }

    private Element removeChild(Element childElement) {
        for (int childIndex = 0; childIndex < children.size(); childIndex++) {
            if (children.get(childIndex).equals(childElement)) {
                return children.remove(childIndex);
            }
        }
        return null;
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        if (!writeEmpty() && children.size() == 0) {
            writer.writeEmptyElement(getPrefix(), getLocalName(), getNamespace());
            for (Attribute attribute : attributes) {
                attribute.toXML(writer);
            }
            return;
        }
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

    protected boolean writeEmpty() {
        return false;
    }
}

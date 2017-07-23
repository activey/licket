package org.licket.xml.dom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author grabslu
 */
public class Element extends Node {

    private Map<String, Attribute> attributes = new HashMap<>();
    private List<Element> children = new LinkedList<>();

    public Element(String localName, String namespace) {
        this("", localName, namespace);
    }

    public Element(String prefix, String localName, String namespace) {
        super(prefix, localName, namespace);
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getLocalName(), attribute);
    }

    public Iterable<Element> children() {
        return children;
    }

    public Iterable<Attribute> attributes() {
        return attributes.values();
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
            // overwrite current element attributes and children manually
            attributes.clear();
            children.clear();

            setLocalName(replacement.getLocalName());
            replacement.attributes().forEach(attribute -> addAttribute(attribute));
            replacement.children().forEach(child -> appendChildElement(child));
            return;
        }
        parentElement.replaceChild(this, replacement);
    }

    public Element detach() {
        Element parentElement = getParent();
        if (parentElement == null) {
            return this;
        }
        Element detached = parentElement.removeChild(this);
//        setParent(null);
        return detached;
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
            for (Attribute attribute : attributes.values()) {
                attribute.toXML(writer);
            }
            return;
        }
        writer.writeStartElement(getPrefix(), getLocalName(), getNamespace());
        for (Attribute attribute : attributes.values()) {
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

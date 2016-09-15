package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author grabslu
 */
public abstract class Node {

    protected String localName;
    private final String namespace;
    private final String prefix;
    private Element parent;

    public Node(String prefix, String localName, String namespace) {
        this.prefix = prefix;
        this.localName = localName;
        this.namespace = namespace;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getNamespace() {
        return namespace;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public abstract void toXML(XMLStreamWriter writer) throws XMLStreamException;
}

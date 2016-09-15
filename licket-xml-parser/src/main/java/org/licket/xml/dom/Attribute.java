package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static org.licket.xml.dom.Attribute.Type.UNDECLARED;

/**
 * @author grabslu
 */
public class Attribute extends Node {

    private String value;

    public Attribute(String prefix, String localName, String namespace, Type type) {
        this(prefix, localName, namespace, "", type);
    }

    public Attribute(String localName, String namespace, String value) {
        this("", localName, namespace, value, UNDECLARED);
    }

    public Attribute(String localName, String namespace) {
        this("", localName, namespace, UNDECLARED);
    }

    public Attribute(String prefix, String localName, String namespace, String value, Type type) {
        super(prefix, localName, namespace);

        this.value = value;
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeAttribute(getLocalName(), getValue());
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        UNDECLARED,
        ID
    }
}

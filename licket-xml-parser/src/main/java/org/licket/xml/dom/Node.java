package org.licket.xml.dom;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;

/**
 * @author grabslu
 */
public abstract class Node {

  private final String namespace;
  private final String prefix;
  protected String localName;
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

  public final byte[] toBytes() throws XMLStreamException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    XMLStreamWriter outputFactory = XMLOutputFactory.newInstance().createXMLStreamWriter(output);
    toXML(outputFactory);
    outputFactory.flush();
    return output.toByteArray();
  }
}

package org.licket.xml.dom;

/**
 * @author grabslu
 */
public class NodeFactory {

  public Element startMakingElement(String localName, String namespace) {
    return new Element(localName, localName);
  }

  public Element finishMakingElement(Element element) {
    return null;
  }

  public Document startMakingDocument() {
    return new Document();
  }

  public void finishMakingDocument(Document document) {

  }

  public Attribute makeAttribute(String name, String namespace, String value, Attribute.Type type) {
    return new Attribute("", name, namespace, value, type);
  }

  public Nodes makeComment(String data) {
    return null;
  }

  public Nodes makeDocType(String rootElementName, String publicID, String systemID) {
    return null;
  }
}

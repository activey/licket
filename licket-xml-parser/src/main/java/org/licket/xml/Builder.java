package org.licket.xml;

import org.licket.xml.dom.Document;
import org.licket.xml.dom.Element;
import org.licket.xml.dom.NodeFactory;
import org.licket.xml.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

import static org.licket.xml.dom.Attribute.Type.UNDECLARED;

/**
 * @author grabslu
 */
public class Builder extends DefaultHandler {

    private NodeFactory nodeFactory;
    private Document document;
    private Element currentElement;
    private Element rootElement;

    public Builder(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    @Override
    public void startDocument() throws SAXException {
        document = nodeFactory.startMakingDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // starting root element
        Element newElement = nodeFactory.startMakingElement(localName, uri);
        if (currentElement == null) {
            rootElement = newElement;
            document.setRootElement(rootElement);
        } else {
            currentElement.appendChildElement(newElement);
        }
        currentElement = newElement;
        addElementAttributes(attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        nodeFactory.finishMakingElement(currentElement);
        currentElement = currentElement.getParent();
    }

    private void addElementAttributes(Attributes attributes) {
        for (int attrIndex = 0; attrIndex < attributes.getLength(); attrIndex++) {
            currentElement.addAttribute(nodeFactory.makeAttribute(attributes.getLocalName(attrIndex), attributes.getURI(attrIndex),
                    attributes.getValue(attrIndex), UNDECLARED));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentElement.appendChildElement(new Text(new String(ch, start, length)));
    }

    @Override
    public void endDocument() throws SAXException {
        nodeFactory.finishMakingDocument(document);
    }

    public final Document build(InputStream xmlStream) throws ParsingException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(xmlStream, this);
            return document;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParsingException(e);
        }
    }
}

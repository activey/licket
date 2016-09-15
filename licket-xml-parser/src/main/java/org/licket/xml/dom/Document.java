package org.licket.xml.dom;

import org.licket.xml.ParsingException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * @author grabslu
 */
public class Document {

    private Element rootElement;

    public String toXML() throws ParsingException {
        StringWriter writer = new StringWriter();
        try {
            XMLStreamWriter outputFactory = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            outputFactory.writeStartDocument();
            rootElement.toXML(outputFactory);
            outputFactory.writeEndDocument();
            return writer.toString();
        } catch (XMLStreamException e) {
            throw new ParsingException(e);
        }
    }

    public void toXML(OutputStream outputStream) throws ParsingException {
        try {
            XMLStreamWriter outputFactory = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
            outputFactory.writeStartDocument();
            rootElement.toXML(outputFactory);
            outputFactory.writeEndDocument();
        } catch (XMLStreamException e) {
            throw new ParsingException(e);
        }
    }

    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }
}

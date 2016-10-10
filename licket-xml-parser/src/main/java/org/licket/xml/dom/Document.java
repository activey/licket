package org.licket.xml.dom;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import org.licket.xml.ParsingException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author grabslu
 */
public class Document {

    private Element rootElement;

    public void toXML(OutputStream outputStream) throws ParsingException, IOException {
        OutputStreamWriter writer = null;
        try {
            XMLStreamWriter streamWriter = streamWriter(writer = new OutputStreamWriter(outputStream));
            rootElement.toXML(streamWriter);
        } catch (XMLStreamException e) {
            throw new ParsingException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    private XMLStreamWriter streamWriter(Writer writer) throws XMLStreamException {
        return new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(writer));
    }

    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }
}

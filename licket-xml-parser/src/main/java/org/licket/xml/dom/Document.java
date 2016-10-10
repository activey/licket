package org.licket.xml.dom;

import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import org.licket.xml.ParsingException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static net.sf.saxon.s9api.Serializer.Property.INDENT;
import static net.sf.saxon.s9api.Serializer.Property.METHOD;

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
        } catch (XMLStreamException | SaxonApiException e) {
            throw new ParsingException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    private XMLStreamWriter streamWriter(Writer writer) throws XMLStreamException, SaxonApiException {
        Configuration config = new Configuration();
        Processor processor = new Processor(config);
        Serializer serializer = processor.newSerializer();
        serializer.setOutputProperty(METHOD, "html");
        serializer.setOutputProperty(INDENT, "yes");
        serializer.setOutputWriter(writer);
        return serializer.getXMLStreamWriter();
    }

    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }
}

package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author grabslu
 */
public class Text extends Element {

    private String text;

    public Text(String text) {
        super("", "");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeCharacters(text);
    }
}

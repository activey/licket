package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static java.lang.String.format;

/**
 * @author activey
 */
public class Comment extends Element {

    private String commentText;

    public Comment(String commentText, String... variables) {
        super("", "");
        this.commentText = format(commentText, variables);
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeComment(commentText);
    }
}

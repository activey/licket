package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author grabslu
 */
public class Nodes extends Node {

    private Node[] nodes = {};

    public Nodes(Node... nodes) {
        super("", "", "");

        this.nodes = nodes;
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        for (Node node : nodes) {
            node.toXML(writer);
        }
    }
}

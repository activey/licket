package org.licket.xml.dom;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author grabslu
 */
public class Nodes extends Node {

    private List<Node> nodes = new LinkedList();

    public Nodes(Node... nodes) {
        super("", "", "");

        for (Node node : nodes) {
            this.nodes.add(node);
        }
    }

    public Nodes add(Node node) {
        nodes.add(node);
        return this;
    }

    @Override
    public void toXML(XMLStreamWriter writer) throws XMLStreamException {
        for (Node node : nodes) {
            node.toXML(writer);
        }
    }
}

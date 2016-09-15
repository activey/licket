package org.licket.xml;

import org.junit.Test;
import org.licket.xml.dom.Document;
import org.licket.xml.dom.NodeFactory;

/**
 * @author grabslu
 */
public class BuilderTest {

    @Test
    public void test() throws ParsingException {
        Document document = new Builder(new NodeFactory()).build(BuilderTest.class.getClassLoader().getResourceAsStream("test.html"));
        System.out.println("---------------------------------");
        System.out.println(document.toXML());
    }
}
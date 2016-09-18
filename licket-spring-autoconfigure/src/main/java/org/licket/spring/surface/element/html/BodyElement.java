package org.licket.spring.surface.element.html;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.licket.core.LicketApplication;
import org.licket.core.resource.ByteArrayResource;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.element.SurfaceElement;
import org.licket.xml.dom.Nodes;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author grabslu
 */
public class BodyElement extends SurfaceElement {

    @Autowired
    private LicketApplication application;

    @Autowired
    private ResourcesStorage resourcesStorage;

    public BodyElement(String name) {
        super(name, NAMESPACE);
    }

    @Override
    protected void onStart() {
        setComponentId(application.getRootComponentContainer().getId());
    }

    @Override
    protected void onFinish() {
        Nodes bodyNodes = new Nodes();
        newArrayList(children()).forEach(child -> bodyNodes.add(child.detach()));

        // TODO to some util method for writing out anyting to XMLStreamWriter
        StringWriter writer = new StringWriter();
        try {
            XMLStreamWriter outputFactory = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            bodyNodes.toXML(outputFactory);
        } catch (XMLStreamException e) {
            return;
        }
        resourcesStorage
            .putResource(new ByteArrayResource(getComponentId(), TEXT_HTML_VALUE, writer.toString().getBytes()));

        appendChildElement(new SurfaceElement(getComponentId(), getNamespace()));
    }
}

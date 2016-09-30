package org.licket.spring.surface.element.html;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import javax.xml.stream.XMLStreamException;
import org.licket.core.LicketApplication;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.ResourceStorage;
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
    private ResourceStorage resourcesStorage;

    public BodyElement(String name) {
        super(name, HTML_NAMESPACE);
    }

    @Override
    protected void onStart() {
        setComponentId(application.rootComponentContainer().getId());
    }

    @Override
    protected void onFinish() {
        Nodes bodyNodes = new Nodes();
        newArrayList(children()).forEach(child -> bodyNodes.add(child.detach()));

        try {
            resourcesStorage
                    .putResource(new ByteArrayResource(getComponentId(), TEXT_HTML_VALUE, bodyNodes.toBytes()));
            appendChildElement(new SurfaceElement(getComponentId(), getNamespace()));
        } catch (XMLStreamException e) {
            return;
        }

    }
}

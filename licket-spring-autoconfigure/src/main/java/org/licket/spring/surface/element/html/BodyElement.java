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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author grabslu
 */
public class BodyElement extends SurfaceElement {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyElement.class);

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
            appendChildElement(newBody());
        } catch (XMLStreamException e) {
            LOGGER.error("An error occured while processing body element.", e);
            return;
        }

        resourcesStorage.getFootJavascriptResources().forEach(resource -> {
            LOGGER.debug("Using foot JS resource: {}", resource.getName());

            ScriptElement scriptElement = new ScriptElement();
            scriptElement.setSrc(resourcesStorage.getResourceUrl(resource));
            addChildElement(scriptElement);
        });
    }

    private SurfaceElement newBody() {
        SurfaceElement surfaceElement = new SurfaceElement(getComponentId(), getNamespace());
        surfaceElement.addAttribute("id", getComponentId());
        return surfaceElement;
    }
}

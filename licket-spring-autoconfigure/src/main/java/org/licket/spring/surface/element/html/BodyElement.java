package org.licket.spring.surface.element.html;
import org.licket.core.LicketApplication;
import org.licket.surface.element.SurfaceElement;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

/**
 * @author grabslu
 */
public class BodyElement extends SurfaceElement {

    @Autowired
    private LicketApplication application;

    public BodyElement(String name) {
        super(name, NAMESPACE);
    }

    @Override
    protected void onStart() {
        setComponentId(application.getRootComponentContainer().getId());
    }

    @Override
    protected void onFinish() {
        SurfaceElement rootComponentElement = new SurfaceElement(getComponentId(), getNamespace());
        newArrayList(children()).forEach(child -> rootComponentElement.appendChildElement(child.detach()));
        appendChildElement(rootComponentElement);
    }
}

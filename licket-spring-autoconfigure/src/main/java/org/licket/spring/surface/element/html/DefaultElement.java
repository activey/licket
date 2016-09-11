package org.licket.spring.surface.element.html;

import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;
import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.resource.ByteArrayResource;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.element.BaseElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class DefaultElement extends BaseElement {

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ResourcesStorage resourcesStorage;

    public DefaultElement(String name) {
        super(name, NAMESPACE);
    }

    @Override
    protected void onFinish() {
        if (!isComponentIdSet()) {
            return;
        }
        // extracting element content as separate licket resource - angular component view
        CompositeId rootRelative = fromStringValueWithAdditionalParts(licketApplication.getRootComponent().getId(),
            getComponentCompositeId().getIdParts());

        // creating new static resource
        resourcesStorage.putResource(new ByteArrayResource(rootRelative.getValue(), "text/html", toXML().getBytes()));

        // setting tag name
        replaceWith(new BaseElement(getComponentId(), getBaseURI()));
        detach();
    }
}

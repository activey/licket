package org.licket.spring.surface.element.html;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;
import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;
import org.licket.spring.resource.ResourcesStorage;
import org.licket.spring.surface.element.render.SpringDrivenComponentRenderingContext;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class DefaultHtmlElement extends SurfaceElement {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHtmlElement.class);

    @Autowired
    private LicketApplication licketApplication;

    @Autowired
    private ResourcesStorage resourcesStorage;

    public DefaultHtmlElement(String name) {
        super(name, HTML_NAMESPACE);
    }

    @Override
    protected final void onFinish() {
        if (!isComponentIdSet()) {
            return;
        }
        CompositeId compositeId = getComponentCompositeId();
        Optional<LicketComponent<?>> componentOptional = licketApplication.findComponent(compositeId);
        if (!componentOptional.isPresent()) {
            LOGGER.trace("Unable to find component: {}", compositeId.getValue());
            return;
        }
        componentOptional.get().render(new SpringDrivenComponentRenderingContext(this, resourcesStorage));
    }
}

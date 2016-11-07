package org.licket.spring.surface.element.html;

import static org.licket.spring.surface.element.html.HtmlElementFactory.HTML_NAMESPACE;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.spring.surface.element.render.SpringDrivenComponentRenderingContext;
import org.licket.surface.SurfaceContext;
import org.licket.surface.element.SurfaceElement;
import org.licket.surface.tag.ElementFactories;
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
    private ResourceStorage resourcesStorage;

    @Autowired
    private ElementFactories surfaceElementFactories;

    public DefaultHtmlElement(String name) {
        super(name, HTML_NAMESPACE);
    }

    @Override
    protected final void onFinish(SurfaceContext surfaceContext) {
        if (!isComponentIdSet()) {
            return;
        }
        CompositeId compositeId = getComponentCompositeId();
        Optional<LicketComponent<?>> componentOptional = licketApplication.findComponent(compositeId);
        if (!componentOptional.isPresent()) {
            LOGGER.trace("Unable to find component: {}", compositeId.getValue());
            return;
        }
        LicketComponent<?> component = componentOptional.get();
        LicketComponentView componentView = component.getView();
        if (componentView.isTemplateExternal()) {
            System.out.println("-------------------------------------------");
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            surfaceContext.processTemplateContent(componentView.viewResource().getStream(), byteArrayStream);
            System.out.println(new String(byteArrayStream.toByteArray()));
            System.out.println("-------------------------------------------");
        }

        componentOptional.get().render(new SpringDrivenComponentRenderingContext(this, resourcesStorage));
    }
}

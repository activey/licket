package org.licket.spring.surface.element.html.compiler;

import org.licket.core.resource.Resource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.surface.SurfaceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.function.Supplier;

/**
 * @author grabslu
 */
public class ComponentTemplateCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentTemplateCompiler.class);

    private Supplier<LicketComponent<?>> componentSupplier;

    public ComponentTemplateCompiler(Supplier<LicketComponent<?>> componentSupplier) {
        this.componentSupplier = componentSupplier;
    }

    public byte[] compile(SurfaceContext surfaceContext) {
        LicketComponent<?> component = componentSupplier.get();
        if (component == null) {
            LOGGER.warn("Unable to find component.");
        }
        LicketComponentView componentView = component.getView();
        return compileComponentTemplate(componentView.viewResource(), surfaceContext).toByteArray();
    }

    private ByteArrayOutputStream compileComponentTemplate(Resource componentViewResource, SurfaceContext surfaceContext) {
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        surfaceContext.processTemplateContent(componentViewResource.getStream(), byteArrayStream);
        return byteArrayStream;
    }
}

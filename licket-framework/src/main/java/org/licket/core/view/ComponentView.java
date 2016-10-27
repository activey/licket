package org.licket.core.view;

import org.licket.core.resource.Resource;
import org.licket.core.resource.html.HtmlResource;
import org.licket.core.view.container.ExternalizedComponentView;
import org.licket.core.view.list.NonExternalizedComponentView;

import java.io.InputStream;

/**
 * @author grabslu
 */
public interface ComponentView {

    static ComponentView fromComponentContainerClass(Class<? extends LicketComponent<?>> componentClass) {
        return fromClassPathResource(componentClass.getSimpleName(), componentClass.getName().replaceAll("\\.", "/").concat(".html"));
    }

    static ComponentView fromClassPathResource(String name, String resourcePath) {
        return new ExternalizedComponentView(new HtmlResource(name, resourcePath));
    }

    static ComponentView internal() {
        return new NonExternalizedComponentView();
    }

    InputStream readViewContent();

    boolean isExternalized();
}

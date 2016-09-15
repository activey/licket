package org.licket.core.view;

import org.licket.core.resource.Resource;
import org.licket.core.resource.html.HtmlResource;
import org.licket.core.view.container.ExternalizedComponentContainerView;
import org.licket.core.view.list.NonExternalizedComponentContainerView;

import java.io.InputStream;

/**
 * @author grabslu
 */
public interface ComponentContainerView {

    static ComponentContainerView fromResource(Resource resource) {
        return new ExternalizedComponentContainerView(resource);
    }

    static ComponentContainerView fromComponentContainerClass(Class<? extends LicketComponent<?>> componentClass) {
        return fromClassPathResource(componentClass.getSimpleName(), componentClass.getName().replaceAll("\\.", "/").concat(".html"));
    }

    static ComponentContainerView fromClassPathResource(String name, String resourcePath) {
        return new ExternalizedComponentContainerView(new HtmlResource("", resourcePath));
    }

    static ComponentContainerView internal() {
        return new NonExternalizedComponentContainerView();
    }

    InputStream readViewContent();

    boolean isExternalized();
}

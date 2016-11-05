package org.licket.core.view;

import java.io.InputStream;
import org.licket.core.resource.Resource;
import org.licket.core.resource.html.HtmlResource;

/**
 * @author grabslu
 */
public interface LicketComponentView {

    static LicketComponentView fromComponentClass(Class<? extends LicketComponent<?>> componentClass) {
        return fromClassPathResource(componentClass.getSimpleName(),
            componentClass.getName().replaceAll("\\.", "/").concat(".html"));
    }

    static LicketComponentView fromClassPathResource(String name, String resourcePath) {
        return new ExternalComponentView(new HtmlResource(name, resourcePath));
    }

    static LicketComponentView internalTemplateView() {
        return new InternalComponentView();
    }

    static LicketComponentView noView() { return new NoTemplateComponentView(); }

    InputStream readViewContent();

    boolean hasTemplate();

    boolean isTemplateExternal();

    class NoTemplateComponentView implements LicketComponentView {

        @Override
        public InputStream readViewContent() {
            return null;
        }

        @Override
        public boolean hasTemplate() {
            return false;
        }

        @Override
        public boolean isTemplateExternal() {
            return false;
        }
    }

    class InternalComponentView implements LicketComponentView {

        @Override
        public InputStream readViewContent() {
            return null;
        }

        @Override
        public boolean hasTemplate() {
            return true;
        }

        @Override
        public boolean isTemplateExternal() {
            return false;
        }
    }

    class ExternalComponentView implements LicketComponentView {

        private Resource componentViewResource;

        public ExternalComponentView(Resource componentViewResource) {
            this.componentViewResource = componentViewResource;
        }

        public final InputStream readViewContent() {
            return componentViewResource.getStream();
        }

        public boolean hasTemplate() {
            return true;
        }

        @Override
        public boolean isTemplateExternal() {
            return true;
        }
    }
}

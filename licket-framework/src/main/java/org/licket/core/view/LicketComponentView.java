package org.licket.core.view;

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

    Resource viewResource();

    boolean hasTemplate();

    boolean isTemplateExternal();

    class NoTemplateComponentView implements LicketComponentView {

        @Override
        public Resource viewResource() {
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
        public Resource viewResource() {
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

        public final Resource viewResource() {
            return componentViewResource;
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

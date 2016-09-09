package org.licket.core.view;

import java.io.InputStream;

public class LicketComponentView {

    private String componentViewTemplate;

    public static LicketComponentView fromComponentClass(Class<? extends AbstractLicketComponent<?>> componentClass) {
        return fromClassPathResource(componentClass.getName().replaceAll("\\.", "/").concat(".html"));
    }

    public static LicketComponentView fromClassPathResource(String resourcePath) {
        return new LicketComponentView(resourcePath);
    }

    public static LicketComponentView fromCurrentMarkup() {
        // TODO
        return new LicketComponentView("");
    }

    private LicketComponentView(String componentViewTemplate) {
        this.componentViewTemplate = componentViewTemplate;
    }

    public InputStream readViewContent() {
        return LicketComponentView.class.getClassLoader().getResourceAsStream(componentViewTemplate);
    }
}

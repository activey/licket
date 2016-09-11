package org.licket.core.view;

import java.io.InputStream;

public class LicketComponentView {

    public static LicketComponentView fromComponentClass(Class<? extends LicketComponent<?>> componentClass) {
        return fromClassPathResource(componentClass.getName().replaceAll("\\.", "/").concat(".html"));
    }

    public static LicketComponentView fromClassPathResource(String resourcePath) {
        return new LicketComponentView(resourcePath);
    }

    public static LicketComponentView fromCurrentMarkup() {
        // TODO think about it
        return new LicketComponentView("");
    }

    private String componentViewLocation;

    private LicketComponentView(String componentViewLocation) {
        this.componentViewLocation = componentViewLocation;
    }

    public InputStream readViewContent() {
        return LicketComponentView.class.getClassLoader().getResourceAsStream(componentViewLocation);
    }
}

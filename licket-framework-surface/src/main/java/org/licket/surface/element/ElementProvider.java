package org.licket.surface.element;

/**
 * @author activey
 */
public interface ElementProvider {

    String getLocalName();

    SurfaceElement provideElement();

    static ElementProvider empty(String localName) {
        return new ElementProvider() {
            @Override
            public String getLocalName() {
                return localName;
            }

            @Override
            public SurfaceElement provideElement() {
                return null;
            }
        };
    }
}

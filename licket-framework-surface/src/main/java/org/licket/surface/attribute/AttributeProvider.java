package org.licket.surface.attribute;

import java.util.function.Supplier;

/**
 * @author activey
 */
public interface AttributeProvider {

    String getLocalName();

    BaseAttribute provideAttribute();

    static AttributeProvider empty(String localName) {
        return new AttributeProvider() {
            @Override
            public String getLocalName() {
                return localName;
            }

            @Override
            public BaseAttribute provideAttribute() {
                return null;
            }
        };
    }
}

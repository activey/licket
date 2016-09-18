package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ObjectLiteral;

import java.util.ArrayList;
import java.util.List;

/**
 * @author activey
 */
public class ObjectLiteralBuilder extends AbstractAstNodeBuilder<ObjectLiteral> {

    private List<ObjectPropertyBuilder> properties = new ArrayList();

    private ObjectLiteralBuilder() {}

    public static ObjectLiteralBuilder objectLiteral() {
        return new ObjectLiteralBuilder();
    }

    public ObjectLiteralBuilder objectProperty(ObjectPropertyBuilder objectProperty) {
        properties.add(objectProperty);
        return this;
    }

    @Override
    public ObjectLiteral build() {
        ObjectLiteral objectLiteral = new ObjectLiteral();
        properties.forEach(property -> objectLiteral.addElement(property.build()));
        return objectLiteral;
    }
}

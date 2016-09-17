package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ObjectLiteral;

/**
 * @author activey
 */
public class ObjectLiteralBuilder extends AbstractAstNodeBuilder<ObjectLiteral> {

    private ObjectLiteralBuilder() {}

    public static ObjectLiteralBuilder objectLiteral() {
        return new ObjectLiteralBuilder();
    }

    @Override
    public ObjectLiteral build() {
        return new ObjectLiteral();
    }
}

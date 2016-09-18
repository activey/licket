package org.licket.framework.hippo;

import org.mozilla.javascript.ast.PropertyGet;

/**
 * @author activey
 */
public class PropertyGetBuilder extends AbstractAstNodeBuilder<PropertyGet> {

    private final AbstractAstNodeBuilder left;
    private final NameBuilder right;

    private PropertyGetBuilder(AbstractAstNodeBuilder left, NameBuilder right) {
        this.left = left;
        this.right = right;
    }

    public static PropertyGetBuilder property(KeywordLiteralBuilder keywordLiteral, NameBuilder right) {
        return new PropertyGetBuilder(keywordLiteral, right);
    }

    public static PropertyGetBuilder property(FunctionCallBuilder functionCall, NameBuilder right) {
        return new PropertyGetBuilder(functionCall, right);
    }

    public static PropertyGetBuilder property(PropertyGetBuilder left, NameBuilder right) {
        return new PropertyGetBuilder(left, right);
    }

    public static PropertyGetBuilder property(NameBuilder left, NameBuilder right) {
        return new PropertyGetBuilder(left, right);
    }

    public PropertyGet build() {
        PropertyGet propertyGet = new PropertyGet();
        propertyGet.setTarget(left.build());
        propertyGet.setProperty(right.build());
        return propertyGet;
    }
}

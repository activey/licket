package org.licket.framework.hippo;

import org.mozilla.javascript.ast.PropertyGet;

import static org.licket.framework.hippo.NameBuilder.name;

/**
 * @author activey
 */
public class PropertyNameBuilder extends AbstractAstNodeBuilder<PropertyGet> {

    private final AbstractAstNodeBuilder left;
    private NameBuilder right;

    public PropertyNameBuilder(AbstractAstNodeBuilder left, NameBuilder right) {
        this.left = left;
        this.right = right;
    }

    public static PropertyNameBuilder property(AbstractAstNodeBuilder<?> astNode, NameBuilder right) {
        return new PropertyNameBuilder(astNode, right);
    }

    public static PropertyNameBuilder property(AbstractAstNodeBuilder<?> astNode, String right) {
        return new PropertyNameBuilder(astNode, name(right));
    }

    public static PropertyNameBuilder property(String left, String right) {
        return new PropertyNameBuilder(name(left), name(right));
    }

    public PropertyNameBuilder right(NameBuilder astNode) {
        this.right = astNode;
        return this;
    }

    public PropertyGet build() {
        PropertyGet propertyGet = new PropertyGet();
        propertyGet.setTarget(left.build());
        propertyGet.setProperty(right.build());
        return propertyGet;
    }
}

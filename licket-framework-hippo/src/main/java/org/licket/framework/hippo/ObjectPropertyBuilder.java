package org.licket.framework.hippo;

import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ObjectProperty;

/**
 * @author activey
 */
public class ObjectPropertyBuilder extends AbstractAstNodeBuilder<ObjectProperty> {

    private NameBuilder name;

    private AbstractAstNodeBuilder<?> value;

    private ObjectPropertyBuilder() {}

    public static ObjectPropertyBuilder propertyBuilder() {
        return new ObjectPropertyBuilder();
    }

    public ObjectPropertyBuilder name(NameBuilder name) {
        this.name = name;
        return this;
    }

    public ObjectPropertyBuilder value(ArrayLiteralBuilder arrayLiteralBuilder) {
        this.value = arrayLiteralBuilder;
        return this;
    }

    public ObjectPropertyBuilder value(StringLiteralBuilder literalBuilder) {
        this.value = literalBuilder;
        return this;
    }

    public ObjectPropertyBuilder value(AbstractAstNodeBuilder<FunctionNode> functionNode) {
        this.value = functionNode;
        return this;
    }

    @Override
    public ObjectProperty build() {
        ObjectProperty objectProperty = new ObjectProperty();
        objectProperty.setLeft(name.build());
        objectProperty.setRight(value.build());
        return objectProperty;
    }
}

package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ObjectProperty;

/**
 * @author activey
 */
public class ObjectPropertyBuilder extends AbstractAstNodeBuilder<ObjectProperty> {

    private AbstractAstNodeBuilder name;

    private AbstractAstNodeBuilder<?> value;

    private ObjectPropertyBuilder() {}

    public static ObjectPropertyBuilder propertyBuilder() {
        return new ObjectPropertyBuilder();
    }

    public ObjectPropertyBuilder name(NameBuilder name) {
        this.name = name;
        return this;
    }

    public ObjectPropertyBuilder name(String name) {
        this.name = NameBuilder.name(name);
        return this;
    }

    public ObjectPropertyBuilder name(StringLiteralBuilder stringLiteral) {
        this.name = stringLiteral;
        return this;
    }

    public ObjectPropertyBuilder arrayValue(ArrayLiteralBuilder arrayLiteralBuilder) {
        this.value = arrayLiteralBuilder;
        return this;
    }

    public ObjectPropertyBuilder value(NameBuilder name) {
        this.value = name;
        return this;
    }

    public ObjectPropertyBuilder value(PropertyNameBuilder propertyGetBuilder) {
        this.value = propertyGetBuilder;
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

    public ObjectPropertyBuilder value(ObjectLiteralBuilder objectLiteral) {
        this.value = objectLiteral;
        return this;
    }

    public ObjectPropertyBuilder value(ArrayLiteralBuilder props) {
        this.value = props;
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

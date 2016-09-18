package org.licket.core.view.angular;

import org.licket.framework.hippo.*;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.ObjectLiteral;

import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.objectProperty;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class ComponentClassBuilder extends AbstractAstNodeBuilder<ObjectLiteral> {

    private ClassConstructorBuilder classConstructorBuilder;

    private ComponentClassBuilder() {}

    public static ComponentClassBuilder classBuilder() {
        return new ComponentClassBuilder();
    }

    public ComponentClassBuilder constructor(ClassConstructorBuilder classConstructor) {
        this.classConstructorBuilder = classConstructor;
        return this;
    }

    @Override
    public ObjectLiteral build() {
        return objectLiteral()
                .objectProperty(objectProperty().name(name("constructor")).value(classConstructorBuilder)).build();
    }
}

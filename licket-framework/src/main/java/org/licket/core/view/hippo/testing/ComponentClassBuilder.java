package org.licket.core.view.hippo.testing;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.mozilla.javascript.ast.ObjectLiteral;

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
                .objectProperty(propertyBuilder().name(name("constructor")).arrayValue(classConstructorBuilder)).build();
    }
}

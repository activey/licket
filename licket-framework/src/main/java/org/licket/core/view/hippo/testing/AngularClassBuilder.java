package org.licket.core.view.hippo.testing;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.mozilla.javascript.ast.ObjectLiteral;

/**
 * @author activey
 */
public class AngularClassBuilder extends AbstractAstNodeBuilder<ObjectLiteral> {

    private ClassConstructorBuilder classConstructorBuilder;

    private AngularClassBuilder() {}

    public static AngularClassBuilder classBuilder() {
        return new AngularClassBuilder();
    }

    public AngularClassBuilder constructor(ClassConstructorBuilder classConstructor) {
        this.classConstructorBuilder = classConstructor;
        return this;
    }

    @Override
    public ObjectLiteral build() {
        return objectLiteral()
                .objectProperty(propertyBuilder().name(name("constructor")).arrayValue(classConstructorBuilder)).build();
    }
}

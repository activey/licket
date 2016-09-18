package org.licket.core.view.angular;

import org.licket.framework.hippo.*;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.FunctionNode;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.keywordLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyGetBuilder.property;

/**
 * @author activey
 */
public class ClassConstructorBuilder extends AbstractAstNodeBuilder<FunctionNode> {

    private ClassConstructorBuilder() {}

    public static ClassConstructorBuilder constructorBuilder() {
        return new ClassConstructorBuilder();
    }

    @Override
    public FunctionNode build() {
        return functionNode().body(
                block()
                        .statement(assignment()
                                .left(property(keywordLiteral(), name("model")))
                                .right(objectLiteral()))
        ).build();
    }
}

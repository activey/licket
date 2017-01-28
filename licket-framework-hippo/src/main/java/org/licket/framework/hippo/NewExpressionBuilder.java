package org.licket.framework.hippo;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.ast.NewExpression;

/**
 * @author activey
 */
public class NewExpressionBuilder extends AbstractAstNodeBuilder<NewExpression> {

    private List<AbstractAstNodeBuilder<?>> arguments = new LinkedList<>();
    private AbstractAstNodeBuilder<?> target;

    private NewExpressionBuilder() {}

    public static NewExpressionBuilder newExpression() {
        return new NewExpressionBuilder();
    }

    public NewExpressionBuilder target(PropertyNameBuilder property) {
        this.target = property;
        return this;
    }

    public NewExpressionBuilder target(NameBuilder name) {
        this.target = name;
        return this;
    }

    public NewExpressionBuilder argument(NameBuilder name) {
        arguments.add(name);
        return this;
    }

    public NewExpressionBuilder argument(ObjectLiteralBuilder objectLiteral) {
        arguments.add(objectLiteral);
        return this;
    }

    public NewExpressionBuilder argument(PropertyNameBuilder propertyBuilder) {
        arguments.add(propertyBuilder);
        return this;
    }

    @Override
    public NewExpression build() {
        NewExpression newExpression = new NewExpression();
        newExpression.setTarget(target.build());
        arguments.forEach(argument -> newExpression.addArgument(argument.build()));
        return newExpression;
    }
}

package org.licket.framework.hippo;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.InfixExpression;

/**
 * @author activey
 */
public class EqualCheckExpressionBuilder extends AbstractAstNodeBuilder<InfixExpression> {

    private AbstractAstNodeBuilder<?> left;
    private AbstractAstNodeBuilder<?> right;

    public static EqualCheckExpressionBuilder equalCheckExpression() {
        return new EqualCheckExpressionBuilder();
    }

    private EqualCheckExpressionBuilder() {}

    public EqualCheckExpressionBuilder left(PropertyNameBuilder left) {
        this.left = left;
        return this;
    }

    public EqualCheckExpressionBuilder right(StringLiteralBuilder right) {
        this.right = right;
        return this;
    }

    @Override
    public InfixExpression build() {
        InfixExpression infixExpression = new InfixExpression();
        infixExpression.setLeft(left.build());
        infixExpression.setRight(right.build());
//        infixExpression.setType(Token.EQ);
        infixExpression.setOperator(Token.EQ);
        return infixExpression;
    }
}

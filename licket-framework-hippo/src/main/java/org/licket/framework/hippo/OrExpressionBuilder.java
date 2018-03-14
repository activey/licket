package org.licket.framework.hippo;

import org.mozilla.javascript.ast.InfixExpression;

import static org.mozilla.javascript.Token.OR;

/**
 * @author activey
 */
public class OrExpressionBuilder extends AbstractAstNodeBuilder<InfixExpression> {

  private AbstractAstNodeBuilder left;
  private AbstractAstNodeBuilder right;

  private OrExpressionBuilder() {
  }

  public static OrExpressionBuilder orExpression() {
    return new OrExpressionBuilder();
  }

  public OrExpressionBuilder left(PropertyNameBuilder propertyNameBuilder) {
    this.left = propertyNameBuilder;
    return this;
  }

  public OrExpressionBuilder right(ParenthesizedExpressionBuilder expressionBuilder) {
    this.right = expressionBuilder;
    return this;
  }

  @Override
  public InfixExpression build() {
    InfixExpression expression = new InfixExpression();
    expression.setOperator(OR);
    expression.setLeft(left.build());
    expression.setRight(right.build());
    return expression;
  }
}

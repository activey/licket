package org.licket.framework.hippo;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.InfixExpression;

/**
 * @author activey
 */
public class NotEqualCheckExpressionBuilder extends AbstractAstNodeBuilder<InfixExpression> {

  private AbstractAstNodeBuilder<?> left;
  private AbstractAstNodeBuilder<?> right;

  private NotEqualCheckExpressionBuilder() {
  }

  public static NotEqualCheckExpressionBuilder notEqualCheckExpression() {
    return new NotEqualCheckExpressionBuilder();
  }

  public NotEqualCheckExpressionBuilder left(PropertyNameBuilder left) {
    this.left = left;
    return this;
  }

  public NotEqualCheckExpressionBuilder left(NameBuilder left) {
    this.left = left;
    return this;
  }

  public NotEqualCheckExpressionBuilder right(StringLiteralBuilder right) {
    this.right = right;
    return this;
  }

  public NotEqualCheckExpressionBuilder right(PropertyNameBuilder right) {
    this.right = right;
    return this;
  }

  public NotEqualCheckExpressionBuilder right(KeywordLiteralBuilder right) {
    this.right = right;
    return this;
  }

  @Override
  public InfixExpression build() {
    InfixExpression infixExpression = new InfixExpression();
    infixExpression.setLeft(left.build());
    infixExpression.setRight(right.build());
    infixExpression.setOperator(Token.NE);
    return infixExpression;
  }
}

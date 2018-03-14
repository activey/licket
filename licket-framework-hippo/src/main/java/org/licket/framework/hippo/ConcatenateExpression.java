package org.licket.framework.hippo;

import org.mozilla.javascript.ast.InfixExpression;

import static org.mozilla.javascript.Token.ADD;

/**
 * @author activey
 */
public class ConcatenateExpression extends AbstractAstNodeBuilder<InfixExpression> {

  private AbstractAstNodeBuilder<?> left;
  private AbstractAstNodeBuilder<?> right;

  private ConcatenateExpression() {
  }

  public static ConcatenateExpression concatenateExpression() {
    return new ConcatenateExpression();
  }

  public ConcatenateExpression left(StringLiteralBuilder stringLiteralBuilder) {
    this.left = stringLiteralBuilder;
    return this;
  }

  public ConcatenateExpression right(AbstractAstNodeBuilder<?> nodeBuilder) {
    this.right = nodeBuilder;
    return this;
  }

  @Override
  public InfixExpression build() {
    InfixExpression expression = new InfixExpression();
    expression.setLeft(left.build());
    expression.setRight(right.build());
    expression.setOperator(ADD);
    return expression;
  }
}

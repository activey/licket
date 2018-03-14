package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ParenthesizedExpression;

/**
 * @author activey
 */
public class ParenthesizedExpressionBuilder extends AbstractAstNodeBuilder<ParenthesizedExpression> {

  private AbstractAstNodeBuilder nodeBuilder;

  private ParenthesizedExpressionBuilder(AbstractAstNodeBuilder nodeBuilder) {
    this.nodeBuilder = nodeBuilder;
  }

  public static ParenthesizedExpressionBuilder parenthesizedExpression(FunctionNodeBuilder functionNodeBuilder) {
    return new ParenthesizedExpressionBuilder(functionNodeBuilder);
  }

  public static ParenthesizedExpressionBuilder parenthesizedAssignment(AssignmentBuilder assignmentBuilder) {
    return new ParenthesizedExpressionBuilder(assignmentBuilder);
  }

  @Override
  public ParenthesizedExpression build() {
    ParenthesizedExpression expression = new ParenthesizedExpression();
    expression.setExpression(nodeBuilder.build());
    return expression;
  }
}

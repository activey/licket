package org.licket.core.view.style;

import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.mozilla.javascript.ast.InfixExpression;

import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class VueClassBindingCondition {

  private final ComponentModelProperty componentModelProperty;
  private AbstractAstNodeBuilder<InfixExpression> infixExpression;

  private VueClassBindingCondition(ComponentModelProperty componentModelProperty) {
    this.componentModelProperty = componentModelProperty;
  }

  public static VueClassBindingCondition when(ComponentModelProperty componentModelProperty) {
    return new VueClassBindingCondition(componentModelProperty);
  }

  public VueClassBindingCondition eq(String componentModelPropertyValue) {
    infixExpression = equalCheckExpression()
            .left(componentModelProperty.builder())
            .right(stringLiteral(componentModelPropertyValue).quoteChar('\''));
    return this;
  }

  public AbstractAstNodeBuilder<InfixExpression> expression() {
    return infixExpression;
  }
}

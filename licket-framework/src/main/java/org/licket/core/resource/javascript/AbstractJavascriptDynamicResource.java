package org.licket.core.resource.javascript;

import org.licket.core.resource.Resource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ExpressionStatementBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.mozilla.javascript.ast.ExpressionStatement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.google.common.net.MediaType.JAVASCRIPT_UTF_8;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.OrExpressionBuilder.orExpression;
import static org.licket.framework.hippo.ParenthesizedExpressionBuilder.parenthesizedAssignment;
import static org.licket.framework.hippo.ParenthesizedExpressionBuilder.parenthesizedExpression;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public abstract class AbstractJavascriptDynamicResource implements Resource {

  public final InputStream getStream() {
    BlockBuilder scriptBlockBuilder = block();
    buildJavascriptTree(scriptBlockBuilder);

    ExpressionStatement expressionStatement = ExpressionStatementBuilder.expressionStatement(
            functionCall()
                    .target(parenthesizedExpression(functionNode()
                            .param(name("app"))
                            .body(scriptBlockBuilder)))
                    .argument(orExpression()
                            .left(windowAppProperty())
                            .right(parenthesizedAssignment(assignment()
                                    .left(windowAppProperty())
                                    .right(objectLiteral()))))
    ).build();
    return new ByteArrayInputStream(expressionStatement.toSource(4).getBytes());
  }

  protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
  }

  @Override
  public final String getMimeType() {
    return JAVASCRIPT_UTF_8.toString();
  }

  private PropertyNameBuilder windowAppProperty() {
    return property(name("window"), name("app"));
  }
}

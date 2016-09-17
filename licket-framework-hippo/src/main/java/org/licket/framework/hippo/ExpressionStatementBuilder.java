package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ExpressionStatement;

/**
 * @author activey
 */
public class ExpressionStatementBuilder extends AbstractAstNodeBuilder<ExpressionStatement> {

    private FunctionCallBuilder functionCallBuilder;

    private ExpressionStatementBuilder(FunctionCallBuilder functionCallBuilder) {
        this.functionCallBuilder = functionCallBuilder;
    }

    public static ExpressionStatementBuilder expressionStatement(FunctionCallBuilder functionCallBuilder) {
        return new ExpressionStatementBuilder(functionCallBuilder);
    }

    @Override
    public ExpressionStatement build() {
        ExpressionStatement expressionStatement = new ExpressionStatement();
        expressionStatement.setExpression(functionCallBuilder.build());
        return expressionStatement;
    }
}

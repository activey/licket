package org.licket.framework.hippo;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.IfStatement;

/**
 * @author activey
 */
public class IfStatementBuilder extends AbstractAstNodeBuilder<IfStatement> {

    private AbstractAstNodeBuilder<?> condition;
    private AbstractAstNodeBuilder<?> thenStatement;

    public static IfStatementBuilder ifStatement() {
        return new IfStatementBuilder();
    }

    private IfStatementBuilder() {}

    public IfStatementBuilder condition(EqualCheckExpressionBuilder condition) {
        this.condition = condition;
        return this;
    }

    public IfStatementBuilder then(AssignmentBuilder then) {
        this.thenStatement = then;
        return this;
    }

    public IfStatementBuilder then(ReturnStatementBuilder then) {
        this.thenStatement = then;
        return this;
    }

    public IfStatementBuilder then(BlockBuilder then) {
        this.thenStatement = then;
        return this;
    }

    @Override
    public IfStatement build() {
        IfStatement ifStatement = new IfStatement();
        ifStatement.setCondition(condition.build());

        AstNode thenNode = thenStatement.build();
//        thenNode.setType(Token.BLOCK);
        ifStatement.setThenPart(thenNode);
        return ifStatement;
    }
}

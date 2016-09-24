package org.licket.framework.hippo;

import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.ReturnStatement;

/**
 * @author activey
 */
public class ReturnStatementBuilder extends AbstractAstNodeBuilder<ReturnStatement> {

    private AbstractAstNodeBuilder<? extends AstNode> returnValue;

    private ReturnStatementBuilder() {}

    public static ReturnStatementBuilder returnStatement() {
        return new ReturnStatementBuilder();
    }

    public ReturnStatementBuilder returnValue(FunctionCallBuilder returnValue) {
        this.returnValue = returnValue;
        return this;
    }

    @Override
    public ReturnStatement build() {
        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.setReturnValue(returnValue.build());
        return returnStatement;
    }
}

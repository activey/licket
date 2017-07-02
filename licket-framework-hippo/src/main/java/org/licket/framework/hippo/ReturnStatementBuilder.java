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

    public ReturnStatementBuilder returnValue(ObjectLiteralBuilder objectLiteral) {
        this.returnValue = objectLiteral;
        return this;
    }

    public ReturnStatementBuilder returnValue(FunctionCallBuilder functionCall) {
        this.returnValue = functionCall;
        return this;
    }

    public ReturnStatementBuilder returnValue(NameBuilder name) {
        this.returnValue = name;
        return this;
    }

    @Override
    public ReturnStatement build() {
        ReturnStatement returnStatement = new ReturnStatement();
        if (returnValue != null) {
            returnStatement.setReturnValue(returnValue.build());
        }
        return returnStatement;
    }
}

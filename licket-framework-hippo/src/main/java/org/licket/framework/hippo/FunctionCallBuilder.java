package org.licket.framework.hippo;

import org.mozilla.javascript.ast.FunctionCall;

import java.util.ArrayList;
import java.util.List;

/**
 * @author activey
 */
public class FunctionCallBuilder extends AbstractAstNodeBuilder<FunctionCall> {

    private AbstractAstNodeBuilder target;
    private List<AbstractAstNodeBuilder> arguments = new ArrayList();

    private FunctionCallBuilder() {}

    public static FunctionCallBuilder functionCall() {
        return new FunctionCallBuilder();
    }

    public FunctionCallBuilder target(ParenthesizedExpressionBuilder parenthesizedExpressionBuilder) {
        this.target = parenthesizedExpressionBuilder;
        return this;
    }

    public FunctionCallBuilder target(PropertyGetBuilder propertyGetBuilder) {
        this.target = propertyGetBuilder;
        return this;
    }

    public FunctionCallBuilder argument(FunctionNodeBuilder functionNodeBuilder) {
        arguments.add(functionNodeBuilder);
        return this;
    }

    public FunctionCallBuilder argument(StringLiteralBuilder stringLiteralBuilder) {
        arguments.add(stringLiteralBuilder);
        return this;
    }

    public FunctionCallBuilder argument(PropertyGetBuilder propertyGetBuilder) {
        arguments.add(propertyGetBuilder);
        return this;
    }

    public FunctionCallBuilder argument(OrExpressionBuilder orExpressionBuilder) {
        arguments.add(orExpressionBuilder);
        return this;
    }

    @Override
    public FunctionCall build() {
        FunctionCall functionCall = new FunctionCall();
        functionCall.setTarget(target.build());
        arguments.forEach(argument -> functionCall.addArgument(argument.build()));
        return functionCall;
    }
}

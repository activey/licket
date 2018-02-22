package org.licket.framework.hippo;

import org.mozilla.javascript.ast.FunctionCall;
import org.mozilla.javascript.ast.ObjectLiteral;

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

    public FunctionCallBuilder target(PropertyNameBuilder propertyNameBuilder) {
        this.target = propertyNameBuilder;
        return this;
    }

    public FunctionCallBuilder target(NameBuilder nameBuilder) {
        this.target = nameBuilder;
        return this;
    }

    public FunctionCallBuilder argument(AbstractAstNodeBuilder<ObjectLiteral> objectLiteral) {
        arguments.add(objectLiteral);
        return this;
    }

    public FunctionCallBuilder argument(NameBuilder nameBuilder) {
        arguments.add(nameBuilder);
        return this;
    }

    public FunctionCallBuilder argument(FunctionNodeBuilder functionNodeBuilder) {
        arguments.add(functionNodeBuilder);
        return this;
    }

    public FunctionCallBuilder argument(FunctionCallBuilder functionCallBuilder) {
        arguments.add(functionCallBuilder);
        return this;
    }

    public FunctionCallBuilder argument(StringLiteralBuilder stringLiteralBuilder) {
        arguments.add(stringLiteralBuilder);
        return this;
    }

    public FunctionCallBuilder argument(PropertyNameBuilder propertyNameBuilder) {
        arguments.add(propertyNameBuilder);
        return this;
    }

    public FunctionCallBuilder argument(OrExpressionBuilder orExpressionBuilder) {
        arguments.add(orExpressionBuilder);
        return this;
    }

    public FunctionCallBuilder argument(ArrayElementGetBuilder arrayElementGetBuilder) {
        arguments.add(arrayElementGetBuilder);
        return this;
    }

    public FunctionCallBuilder argument(KeywordLiteralBuilder keywordLiteralBuilder) {
        arguments.add(keywordLiteralBuilder);
        return this;
    }

    public FunctionCallBuilder argument(ConcatenateExpression infixExpressionBuilder) {
        arguments.add(infixExpressionBuilder);
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

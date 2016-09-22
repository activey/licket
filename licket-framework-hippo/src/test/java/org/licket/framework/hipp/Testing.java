package org.licket.framework.hipp;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.OrExpressionBuilder.orExpression;
import static org.licket.framework.hippo.ParenthesizedExpressionBuilder.parenthesizedAssignment;
import static org.licket.framework.hippo.ParenthesizedExpressionBuilder.parenthesizedExpression;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import org.junit.Test;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ExpressionStatement;

/**
 * @author grabslu
 */
public class Testing {

    @Test
    public void test2() {
        ExpressionStatement expressionStatement = expressionStatement(
            functionCall()
                .target(parenthesizedExpression(functionNode()
                        .param(name("app"))
                        .body(block().prependStatement(expressionStatement(functionCall()
                                .argument(stringLiteral("DOMContentLoaded"))
                                .argument(functionNode().body(
                                        block().prependStatement(functionCall()
                                                .argument(property(name("app"), name("TodosComponent")))
                                                .target(property(property(property(name("ng"), name("platform")), name("browser")), name("bootstrap"))))))
                                .target(property(name("document"), name("addEventListener"))))))))
                .argument(orExpression()
                        .left(property(name("window"), name("app")))
                        .right(parenthesizedAssignment(assignment()
                                .left(property(name("window"), name("app")))
                                .right(objectLiteral()))))
        ).build();
        System.out.println(expressionStatement.toSource(4));
    }

    @Test
    public void test() throws IOException {
        AstRoot astRoot = new Parser().parse(
            new InputStreamReader(Test.class.getClassLoader().getResourceAsStream("TestBoot.js")), "test.js", 0);

        astRoot.forEach(visitor());
    }

    private Consumer<Node> visitor() {
        return astNode -> {
            System.out.println(astNode.getClass().getName());
            System.out.printf("---------------");

            astNode.forEach(visitor());
        };
    }
}

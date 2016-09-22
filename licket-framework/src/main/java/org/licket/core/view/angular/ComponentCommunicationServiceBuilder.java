package org.licket.core.view.angular;

import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.ast.ExpressionStatement;

import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyGetBuilder.property;

/**
 * @author activey
 */
public class ComponentCommunicationServiceBuilder extends AbstractAstNodeBuilder<ExpressionStatement> {

    private String name;

    private ComponentCommunicationServiceBuilder() {}

    public static ComponentCommunicationServiceBuilder componentCommunicationService() {
        return new ComponentCommunicationServiceBuilder();
    }

    public ComponentCommunicationServiceBuilder serviceName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ExpressionStatement build() {
        return expressionStatement(assignment()
                .left(property(name("app"), name(name)))
                .right(functionCall()
                        .target(property(property(name("ng"), name("core")), name("Class")))
                        .argument(serviceConstructor())))
                .build();
    }

    private ObjectLiteralBuilder serviceConstructor() {
        return objectLiteral()
                .objectProperty(propertyBuilder()
                        .name("constructor")
                        .arrayValue(arrayLiteral()
                                .element(property(property(name("ng"), name("http")), name("Http")))
                                .element(invokeComponentAction()
                                        .body(block().appendStatement(
                                                expressionStatement(assignment()
                                                        .left(property(thisLiteral(), name("http")))
                                                        .right(name("http"))
                                                )))
                                        .param(name("http"))
                                )))
                .objectProperty(propertyBuilder().name("invokeComponentAction").value(invokeComponentAction()));
    }

    private FunctionNodeBuilder invokeComponentAction() {
        return functionNode().body(block());
    }
}

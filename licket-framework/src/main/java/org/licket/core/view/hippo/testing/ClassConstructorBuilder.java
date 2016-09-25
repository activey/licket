package org.licket.core.view.hippo.testing;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;
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
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.ArrayLiteral;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author activey
 */
public class ClassConstructorBuilder extends AbstractAstNodeBuilder<ArrayLiteral> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassConstructorBuilder.class);

    private static final String PROPERTY_MODEL = "model";
    private static final String PROPERTY_COMPOSITE_ID = "compositeId";
    private static final String PROPERTY_INVOKE_ACTION = "invokeAction";

    private LicketComponentContainer<?> container;

    private ClassConstructorBuilder(LicketComponentContainer<?> container) {
        this.container = container;
    }

    public static ClassConstructorBuilder constructorBuilder(LicketComponentContainer<?> container) {
        return new ClassConstructorBuilder(container);
    }

    @Override
    public ArrayLiteral build() {
        return ArrayLiteralBuilder.arrayLiteral()
                .element(property(name("app"), name("ComponentCommunicationService")))
                .element(functionNode()
                        .param(name("LicketRemote"))
                        .body(block()
                            .appendStatement(
                                expressionStatement(
                                    assignment()
                                        .left(property(thisLiteral(), name(PROPERTY_MODEL)))
                                        .right(componentInitialModel())))
                            .appendStatement(
                                expressionStatement(
                                    assignment()
                                        .left(property(thisLiteral(), name(PROPERTY_COMPOSITE_ID)))
                                        .right(stringLiteral(container.getCompositeId().getValue()))))
                            .appendStatement(
                                expressionStatement(
                                    assignment()
                                        .left(property(thisLiteral(), name(PROPERTY_INVOKE_ACTION)))
                                        .right(invokeComponentAction())
                                )
                            )))
            .build();
        }

    private FunctionNodeBuilder invokeComponentAction() {
        return functionNode().
                body(
                    block()
                        .appendStatement(expressionStatement(
                                variableDeclaration()
                                        .variable(variableInitializer().target(name("_this")).initializer(thisLiteral()))))
                        .appendStatement(
                            expressionStatement(functionCall()
                                .target(property(name("LicketRemote"), name("invokeComponentAction")))
                                .argument(objectLiteral()
                                        .objectProperty(propertyBuilder()
                                                .name("compositeId")
                                                .value(stringLiteral(container.getCompositeId().getValue())))
                                        .objectProperty(propertyBuilder()
                                                .name("childCompositeId")
                                                .value(name("callerId")))
                                        .objectProperty(propertyBuilder()
                                                .name("model")
                                                .value(property(thisLiteral(), name("model"))))
                                )
                                .argument(updateComponentModelHandler())
                                .argument(stringLiteral("invokeAction")))))
                .param(name("callerId"));
    }

    private FunctionNodeBuilder updateComponentModelHandler() {
        return functionNode()
                .param(name("response")).body(
                    block()
                        .appendStatement(expressionStatement(functionCall()
                                .target(property(name("console"), name("log")))
                                .argument(functionCall().target(property(name("response"), name("json")))))
                        )
                        .appendStatement(expressionStatement(
                                assignment()
                                        .left(property(name("_this"), name("model")))
                                        .right(property(functionCall().target(property(name("response"), name("json"))), name("modelObject")))
                        )));
    }

    // TODO very experimental, rewrite!
    private ObjectLiteralBuilder componentInitialModel() {
        ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(QUOTE_FIELD_NAMES, false);

            // serialize component model to string json
            String modelStringValue = mapper.writeValueAsString(container.getComponentModel().get());

            // parse model declaration object literal
            AstRoot astRoot = new Parser().parse(modelObjectLiteralReader(modelStringValue), "test.js", 0);
            astRoot.visitAll(node -> {
                if (node instanceof ObjectLiteral) {
                    objectLiteralBuilder.fromObjectLiteral((ObjectLiteral) node);
                    return false;
                }
                return true;
            });
        } catch (IOException e) {
            LOGGER.error("An error occurred while creating Angular class constructor.", e);
        }
        return objectLiteralBuilder;
    }

    private Reader modelObjectLiteralReader(String modelStringValue) {
        return new StringReader(format("model = %s", modelStringValue));
    }
}

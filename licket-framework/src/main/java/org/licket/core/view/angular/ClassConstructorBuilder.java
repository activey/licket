package org.licket.core.view.angular;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ObjectLiteral;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class ClassConstructorBuilder extends AbstractAstNodeBuilder<FunctionNode> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassConstructorBuilder.class);

    private static final String PROPERTY_MODEL = "model";
    private static final String PROPERTY_COMPOSITE_ID = "compositeId";

    private LicketComponentContainer<?> container;

    private ClassConstructorBuilder(LicketComponentContainer<?> container) {
        this.container = container;
    }

    public static ClassConstructorBuilder constructorBuilder(LicketComponentContainer<?> container) {
        return new ClassConstructorBuilder(container);
    }

    @Override
    public FunctionNode build() {
        return functionNode()
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
                                .right(stringLiteral(container.getCompositeId().getValue())))))
            .build();
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

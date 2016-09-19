package org.licket.core.view.angular;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.*;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.keywordLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyGetBuilder.property;

/**
 * @author activey
 */
public class ClassConstructorBuilder extends AbstractAstNodeBuilder<FunctionNode> {

    private LicketComponentContainer<?> container;

    private ClassConstructorBuilder(LicketComponentContainer<?> container) {
        this.container = container;
    }

    public static ClassConstructorBuilder constructorBuilder(LicketComponentContainer<?> container) {
        return new ClassConstructorBuilder(container);
    }

    @Override
    public FunctionNode build() {
        return functionNode().body(
                block()
                        .statement(assignment()
                                .left(property(keywordLiteral(), name("model")))
                                .right(initializeComponentModel()))
        ).build();
    }

    // TODO very experimental, rewrite!
    private ObjectLiteralBuilder initializeComponentModel() {
        ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
        try {
            ObjectMapper mapper = new ObjectMapper();

            StringWriter modelWriter = new StringWriter();
            mapper.writeValue(modelWriter, container.getComponentModel().get());

            JsonNode valueNode = mapper.readTree(modelWriter.toString());
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.set("value", valueNode);

            modelWriter = new StringWriter();
            mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
            mapper.writeValue(modelWriter, valueNode);

            AstRoot astRoot = new Parser().parse(new StringReader("model = " + modelWriter.toString()), "test.js", 0);
            astRoot.visitAll(node -> {
                if (node instanceof ObjectLiteral) {
                    objectLiteralBuilder.fromObjectLiteral((ObjectLiteral) node);
                    return false;
                }
                return true;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectLiteralBuilder;
    }
}

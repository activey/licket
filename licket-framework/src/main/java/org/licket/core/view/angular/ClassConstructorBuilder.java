package org.licket.core.view.angular;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.keywordLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ObjectLiteral;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
        return functionNode()
            .body(block().prependStatement(
                assignment()
                        .left(property(keywordLiteral(), name("model")))
                        .right(componentInitialModel())))
            .build();
    }

    // TODO very experimental, rewrite!
    private ObjectLiteralBuilder componentInitialModel() {
        ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
        try {
            ObjectMapper mapper = new ObjectMapper();

            StringWriter modelWriter = new StringWriter();
            mapper.writeValue(modelWriter, container.getComponentModel().get());

            JsonNode valueNode = mapper.readTree(modelWriter.toString());
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.set("value", valueNode);

            modelWriter = new StringWriter();
            mapper.configure(QUOTE_FIELD_NAMES, false);
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

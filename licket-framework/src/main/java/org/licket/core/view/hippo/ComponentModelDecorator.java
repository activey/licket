package org.licket.core.view.hippo;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.licket.core.model.LicketComponentModel;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class ComponentModelDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentModelDecorator.class);

    private final ObjectMapper mapper;
    private LicketComponentModel<?> componentModel;

    private ComponentModelDecorator(LicketComponentModel<?> componentModel) {
        this.componentModel = componentModel;

        this.mapper = new ObjectMapper();
        mapper.configure(QUOTE_FIELD_NAMES, false);
    }

    public static ComponentModelDecorator fromComponentModel(LicketComponentModel<?> componentModel) {
        return new ComponentModelDecorator(componentModel);
    }

    public ObjectLiteralBuilder decorate(ObjectLiteralBuilder objectLiteral) {
        return componentModel
            .get()
            .map(this::modelValueToString)
            .map(this::astRootFromString)
            .map(astRoot -> {
                astRoot.visitAll(node -> {
                    if (node instanceof ObjectLiteral) {
                        objectLiteral.fromObjectLiteral((ObjectLiteral) node);
                        return false;
                    }
                    return true;
                });
                return objectLiteral;
            })
            .orElse(objectLiteral);
    }

    private String modelValueToString(Object modelValue) {
        try {
            return mapper.writeValueAsString(modelValue);
        } catch (JsonProcessingException e) {
            LOGGER.error("An error occurred while serializing model value.", e);
            return mapper.createObjectNode().toString();
        }
    }

    private AstRoot astRootFromString(String astString) {
        try {
            return new Parser().parse(modelObjectLiteralReader(astString), "ComponentModelDecorator.js", 0);
        } catch (IOException e) {
            LOGGER.error("An error occurred while parsing AST.", e);
            return new AstRoot();
        }
    }

    private Reader modelObjectLiteralReader(String modelStringValue) {
        return new StringReader(format("model = %s", modelStringValue));
    }
}

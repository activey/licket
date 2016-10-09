package org.licket.core.view.hippo;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.licket.core.model.LicketModel;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author activey
 */
public class ComponentModelSerializer {

    public static ObjectLiteralBuilder serializeComponentModel(LicketModel<?> componentModel) throws IOException {
        ObjectLiteralBuilder modelProperty = ObjectLiteralBuilder.objectLiteral();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(QUOTE_FIELD_NAMES, false);

        // serialize component model to string json
        String modelStringValue = mapper.writeValueAsString(componentModel.get());

        // parse model declaration object literal
        AstRoot astRoot = new Parser().parse(modelObjectLiteralReader(modelStringValue), "test.js", 0);
        astRoot.visitAll(node -> {
            if (node instanceof ObjectLiteral) {
                modelProperty.fromObjectLiteral((ObjectLiteral) node);
                return false;
            }
            return true;
        });
        return modelProperty;
    }

    private static Reader modelObjectLiteralReader(String modelStringValue) {
        return new StringReader(format("model = %s", modelStringValue));
    }
}

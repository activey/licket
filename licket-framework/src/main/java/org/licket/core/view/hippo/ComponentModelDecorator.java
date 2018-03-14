package org.licket.core.view.hippo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.licket.core.model.LicketComponentModel;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;

/**
 * @author activey
 */
public class ComponentModelDecorator {

  private LicketComponentModel<?> componentModel;

  private ComponentModelDecorator(LicketComponentModel<?> componentModel) {
    this.componentModel = componentModel;
  }

  public static ComponentModelDecorator fromComponentModel(LicketComponentModel<?> componentModel) {
    return new ComponentModelDecorator(componentModel);
  }

  private static Reader modelObjectLiteralReader(String modelStringValue) {
    return new StringReader(format("model = %s", modelStringValue));
  }

  public ObjectLiteralBuilder decorate(ObjectLiteralBuilder objectLiteral) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(QUOTE_FIELD_NAMES, false);

    // serialize component model to string json
    Object modelValue = componentModel.get();
    if (modelValue == null) {
      return objectLiteral;
    }
    String modelStringValue = mapper.writeValueAsString(modelValue);

    // parse model declaration object literal
    AstRoot astRoot = new Parser().parse(modelObjectLiteralReader(modelStringValue), "test.js", 0);
    astRoot.visitAll(node -> {
      if (node instanceof ObjectLiteral) {
        objectLiteral.fromObjectLiteral((ObjectLiteral) node);
        return false;
      }
      return true;
    });
    return objectLiteral;
  }
}

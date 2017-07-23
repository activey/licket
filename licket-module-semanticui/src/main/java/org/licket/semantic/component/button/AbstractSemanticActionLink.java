package org.licket.semantic.component.button;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author lukaszgrabski
 */
public class AbstractSemanticActionLink<T> extends AbstractLicketActionLink<T> {

  public AbstractSemanticActionLink(String id, Class<T> modelClass, LicketRemote licketRemote, LicketComponentModelReloader modelReloader) {
    super(id, modelClass, licketRemote, modelReloader);
  }

  @VueComponentFunction
  public void showLoading(BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property(element(), name("addClass")))
                    .argument(stringLiteral("loading"))
    ));
  }

  @VueComponentFunction
  public void hideLoading(BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property(element(), name("removeClass")))
                    .argument(stringLiteral("loading"))
    ));
  }

  private FunctionCallBuilder element() {
    return functionCall()
            .target(name("$"))
            .argument(property(thisLiteral(), name("$el")));
  }

  public SemanticActionLinkAPI api(ComponentFunctionCallback componentFunctionCallback) {
    return new SemanticActionLinkAPI(this, componentFunctionCallback);
  }
}

package org.licket.semantic.component.form;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.form.AbstractLicketForm;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
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
public abstract class AbstractSemanticUIForm<T> extends AbstractLicketForm<T> {

  public AbstractSemanticUIForm(String id, Class<T> modelClass, LicketComponentModel<T> model, LicketComponentView componentView) {
    super(id, modelClass, model, componentView);
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

  @Override
  public AbstractSemanticUIFormAPI api(ComponentFunctionCallback functionCallback) {
    return new AbstractSemanticUIFormAPI(this, functionCallback);
  }
}

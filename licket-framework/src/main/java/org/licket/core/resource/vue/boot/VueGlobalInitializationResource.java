package org.licket.core.resource.vue.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.hippo.vue.component.VueComponentPropertiesDecorator;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class VueGlobalInitializationResource extends AbstractJavascriptDynamicResource implements FootParticipatingResource {

  @Autowired
  private LicketApplication application;

  @Autowired
  private VueComponentPropertiesDecorator componentPropertiesDecorator;

  @Override
  public String getName() {
    return "Licket.application.global.js";
  }

  @Override
  protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
    application.traverseDown(licketComponent -> {
      if (licketComponent.isStateful()) {
        return true;
      }

      scriptBlockBuilder.appendStatement(
              expressionStatement(functionCall()
                      .target(property("Vue", "component"))
                      // TODO its not really unique ...
                      .argument(stringLiteral(licketComponent.getCompositeId().getNormalizedValue()))
                      .argument(componentPropertiesDecorator.decorate(licketComponent, objectLiteral()))));
      return true;
    });
  }
}

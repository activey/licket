package org.licket.core.module.application;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;

import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.falseLiteral;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.KeywordLiteralBuilder.trueLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
public class LicketComponentModelReloader implements ApplicationModuleService {

  @Name("eventHub")
  private PropertyNameBuilder eventHub = property("app", "ApplicationEventHub");

  public static NameBuilder serviceName() {
    return name("$licketModelReloader");
  }

  public static FunctionCallBuilder callReloadComponent(LicketComponent<?> component, boolean patch) {
    if (patch) {
      return functionCall().target(property(property(thisLiteral(), LicketComponentModelReloader.serviceName()), name("notifyModelPatched")))
              .argument(stringLiteral(component.getCompositeId().getValue()))
              .argument(arrayElementGet()
                      .target(property(property("response", "body"), name("patch")))
                      .element(stringLiteral(component.getCompositeId().getValue())));
    }
    return functionCall().target(property(property(thisLiteral(), LicketComponentModelReloader.serviceName()), name("notifyModelChanged")))
            .argument(stringLiteral(component.getCompositeId().getValue()))
            .argument(arrayElementGet()
                    .target(property(property("response", "body"), name("model")))
                    .element(stringLiteral(component.getCompositeId().getValue())));
  }

  @VueComponentFunction
  public void listenForModelChange(@Name("changeListener") NameBuilder changeListener, BlockBuilder body) {
    body.appendStatement(expressionStatement(functionCall()
            .target(property(name("eventHub"), name("$on")))
            .argument(modelChangedEvent())
            .argument(functionNode()
                    .param(name("changedModelData"))
                    .body(block().appendStatement(expressionStatement(
                            functionCall()
                                    .target(changeListener)
                                    .argument(name("changedModelData"))
                                    .argument(falseLiteral())
                    ))))));

    body.appendStatement(expressionStatement(functionCall()
            .target(property(name("eventHub"), name("$on")))
            .argument(modelPatchedEvent())
            .argument(functionNode()
                    .param(name("changedModelData"))
                    .body(block().appendStatement(expressionStatement(
                            functionCall()
                                    .target(changeListener)
                                    .argument(name("changedModelData"))
                                    .argument(trueLiteral())
                    ))))));
  }

  @VueComponentFunction
  public void notifyModelChanged(@Name("compositeId") NameBuilder compositeId,
                                 @Name("changedModelData") NameBuilder changedModelData, BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall().target(property(name("eventHub"), name("$emit")))
                    .argument(modelChangedEvent())
                    .argument(changedModelEventData(compositeId, changedModelData))));
  }

  private StringLiteralBuilder modelChangedEvent() {
    return stringLiteral("component-model-changed");
  }

  private ObjectLiteralBuilder changedModelEventData(NameBuilder compositeId, NameBuilder changedModelData) {
    return objectLiteral()
            .objectProperty(propertyBuilder().name("compositeId").value(compositeId))
            .objectProperty(propertyBuilder().name("model").value(changedModelData));
  }

  @VueComponentFunction
  public void notifyModelPatched(@Name("compositeId") NameBuilder compositeId,
                                 @Name("patchedModelData") NameBuilder patchedModelData, BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall().target(property(name("eventHub"), name("$emit")))
                    .argument(modelPatchedEvent())
                    .argument(patchedModelEventData(compositeId, patchedModelData))));
  }

  private StringLiteralBuilder modelPatchedEvent() {
    return stringLiteral("component-model-patched");
  }

  private ObjectLiteralBuilder patchedModelEventData(NameBuilder compositeId, NameBuilder patchedModelData) {
    return objectLiteral()
            .objectProperty(propertyBuilder().name("compositeId").value(compositeId))
            .objectProperty(propertyBuilder().name("patch").value(patchedModelData));
  }

  @Override
  public NameBuilder vueName() {
    return LicketComponentModelReloader.serviceName();
  }
}

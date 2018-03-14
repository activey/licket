package org.licket.core.module.application;

import org.licket.core.module.resource.HttpCommunicationService;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
public class LicketRemote implements ApplicationModuleService {

  @Name("http")
  private final HttpCommunicationService httpCommunicationService;

  public LicketRemote(HttpCommunicationService httpCommunicationService) {
    this.httpCommunicationService = checkNotNull(httpCommunicationService,
            "Http communication service reference must not be null!");
  }

  public static NameBuilder serviceName() {
    return name("$licketRemoteService");
  }

  public static FunctionCallBuilder callSubmitForm(String formId, PropertyNameBuilder callbackFunction) {
    return functionCall()
            .target(property(property(thisLiteral(), LicketRemote.serviceName()), name("submitForm")))
            .argument(stringLiteral(formId))
            .argument(property(thisLiteral(), name("model")))
            .argument(callbackFunction);
  }

  public static FunctionCallBuilder callMountComponent(String componentId, FunctionNodeBuilder callbackFunction) {
    return functionCall()
            .target(property(property(name("vm"), LicketRemote.serviceName()), name("mountComponent")))
            .argument(stringLiteral(componentId))
            .argument(property(name("to"), name("params")))
            .argument(callbackFunction);
  }

  @VueComponentFunction
  public void submitForm(@Name("formComponentCompositeId") NameBuilder formComponentCompositeId,
                         @Name("formData") NameBuilder formData,
                         @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
    body.appendStatement(expressionStatement(httpCommunicationService
            .callHttpPostWithData("`/licket/form/submit/${formComponentCompositeId}`", formData, responseListener)));
  }

  @VueComponentFunction
  public void handleActionLinkClick(@Name("linkComponentCompositeId") NameBuilder linkComponentCompositeId,
                                    @Name("modelData") NameBuilder modelData,
                                    @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
    body.appendStatement(expressionStatement(httpCommunicationService
            .callHttpPostWithData("`/licket/link/click/${linkComponentCompositeId}`", modelData, responseListener)));
  }

  @VueComponentFunction
  public void mountComponent(@Name("componentCompositeId") NameBuilder componentCompositeId,
                             @Name("mountingParams") NameBuilder mountingParams,
                             @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
    body.appendStatement(expressionStatement(httpCommunicationService
            .callHttpPostWithData("`/licket/component/${componentCompositeId}/mount`", mountingParams, responseListener)));
  }

  @Override
  public NameBuilder vueName() {
    return LicketRemote.serviceName();
  }
}

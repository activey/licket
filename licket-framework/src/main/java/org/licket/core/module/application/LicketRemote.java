package org.licket.core.module.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.module.resource.HttpCommunicationService;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author grabslu
 */
public class LicketRemote implements VueClass {

    @Name("http")
    private final HttpCommunicationService httpCommunicationService;

    public LicketRemote(HttpCommunicationService httpCommunicationService) {
        this.httpCommunicationService = checkNotNull(httpCommunicationService,
            "Http communication service reference must not be null!");
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
                                      @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(httpCommunicationService
            .callHttpPost("`/licket/link/click/${linkComponentCompositeId}`", responseListener)));
    }

    @Override
    public NameBuilder vueName() {
        return name("$licketRemoteService");
    }

    public FunctionCallBuilder callSubmitForm(String formId) {
        return functionCall()
                .target(property(vueName(), name("submitForm")))
                .argument(stringLiteral(formId)).argument(property(thisLiteral(), name("model")))
                .argument(functionNode().param(name("response")).body(block().appendStatement(
                        expressionStatement(
                                functionCall()
                                        .target(property(name("vm"), name("afterSubmit")))
                                        .argument(name("response"))
                        )
                )));
    }
}

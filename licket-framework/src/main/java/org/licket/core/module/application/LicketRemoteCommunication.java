package org.licket.core.module.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.module.resource.HttpCommunicationService;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author grabslu
 */
public class LicketRemoteCommunication implements VueClass {

    @Name("http")
    private final HttpCommunicationService httpCommunicationService;

    public LicketRemoteCommunication(HttpCommunicationService httpCommunicationService) {
        this.httpCommunicationService = checkNotNull(httpCommunicationService,
            "Http communication service reference must not be null!");
    }

    @VueComponentFunction
    public void submitForm(@Name("formComponentCompositeId") NameBuilder formComponentCompositeId,
                           @Name("formData") NameBuilder formData,
                           @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(httpCommunicationService
            .executeHttpPostWithData("`/licket/form/submit/${formComponentCompositeId}`", formData, responseListener)));
    }

    @VueComponentFunction
    public void handleActionLinkClick(@Name("linkComponentCompositeId") NameBuilder linkComponentCompositeId,
                                      @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(httpCommunicationService
            .executeHttpPost("`/licket/link/click/${linkComponentCompositeId}`", responseListener)));
    }

    @Override
    public PropertyNameBuilder vueName() {
        return property(name("app"), name("ComponentCommunicationService"));
    }
}

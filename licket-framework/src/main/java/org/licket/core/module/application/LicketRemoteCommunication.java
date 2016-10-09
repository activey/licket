package org.licket.core.module.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.module.http.HttpCommunicationService;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.core.view.hippo.ngclass.AngularClass;
import org.licket.core.view.hippo.ngclass.AngularInjectable;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author grabslu
 */
public class LicketRemoteCommunication implements AngularClass, AngularInjectable {

    @Name("http")
    private final HttpCommunicationService httpCommunicationService;


    public LicketRemoteCommunication(HttpCommunicationService httpCommunicationService) {
        this.httpCommunicationService = checkNotNull(httpCommunicationService,
            "Http communication service reference must not be null!");
    }

    @AngularClassFunction
    public void submitForm(@Name("formComponentCompositeId") NameBuilder formComponentCompositeId,
                           @Name("formData") NameBuilder formData,
                           @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(
            executeHttpPostWithData("`/licket/form/submit/${formComponentCompositeId}`", formData, responseListener)));
    }

    @AngularClassFunction
    public void handleActionLinkClick(@Name("linkComponentCompositeId") NameBuilder formComponentCompositeId,
                                      @Name("responseListener") NameBuilder responseListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(
            executeHttpPost("`/licket/link/click/${linkComponentCompositeId}`", responseListener)));
    }

    private FunctionCallBuilder executeHttpPost(String url, NameBuilder responseListener) {
        return functionCall()
                .target(property(functionCall()
                                .target(httpPostFunction())
                                .argument(name(url)),
                        subscribeHandlerFunction()))
                .argument(responseListener);
    }

    private FunctionCallBuilder executeHttpPostWithData(String url, NameBuilder data, NameBuilder responseListener) {
        return functionCall()
            .target(property(functionCall()
                            .target(httpPostFunction())
                            .argument(name(url))
                            .argument(data),
                subscribeHandlerFunction()))
            .argument(responseListener);
    }

    private PropertyNameBuilder httpPostFunction() {
        return property(name("http"), name("post"));
    }

    private NameBuilder subscribeHandlerFunction() {
        return name("subscribe");
    }

    @Override
    public PropertyNameBuilder angularName() {
        return PropertyNameBuilder.property(name("app"), name("ComponentCommunicationService"));
    }
}

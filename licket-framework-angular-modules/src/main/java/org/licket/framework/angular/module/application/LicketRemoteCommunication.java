package org.licket.framework.angular.module.application;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author grabslu
 */
public class LicketRemoteCommunication implements AngularClass, AngularInjectable {

    @AngularClassFunction
    public void invokeComponentAction(@Name("actionData") NameBuilder actionData,
                                      @Name("responseListener") NameBuilder responseListener,
                                      @Name("method") NameBuilder method,
                                      BlockBuilder body) {
        body.appendStatement(expressionStatement(executeHttpPost(actionData, responseListener)));
    }

    private FunctionCallBuilder executeHttpPost(NameBuilder actionData, NameBuilder responseListener) {
        return functionCall()
                .target(property(
                        functionCall()
                                .target(httpPostFunction())
                                .argument(name("`/licket/component/action/${method}`"))
                                .argument(actionData),
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

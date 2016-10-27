package org.licket.core.module.resource;

import org.licket.core.view.hippo.angular.ngclass.AngularInjectable;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author grabslu
 */
public class HttpCommunicationService implements VueClass {

    @Override
    public PropertyNameBuilder vueName() {
        return property(thisLiteral(), name("$http"));
    }

    public FunctionCallBuilder executeHttpPost(String url, NameBuilder responseListener) {
        return functionCall()
                .target(property(functionCall()
                                .target(httpPostFunction())
                                .argument(name(url)),
                        subscribeHandlerFunction()))
                .argument(responseListener);
    }

    public FunctionCallBuilder executeHttpPostWithData(String url, NameBuilder data, NameBuilder responseListener) {
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

}

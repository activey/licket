package org.licket.core.module.resource;

import org.licket.core.module.application.ApplicationModuleService;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author grabslu
 */
public class HttpCommunicationService implements ApplicationModuleService {

  @Override
  public NameBuilder vueName() {
    return name("$http");
  }

  public FunctionCallBuilder callHttpPost(String url, NameBuilder responseListener) {
    return functionCall()
            .target(property(functionCall()
                            .target(httpPostFunction())
                            .argument(name(url)),
                    responseCallbackFunction()))
            .argument(responseListener);
  }

  public FunctionCallBuilder callHttpPostWithData(String url, NameBuilder data, NameBuilder responseListener) {
    return functionCall()
            .target(property(functionCall()
                            .target(httpPostFunction())
                            .argument(name(url))
                            .argument(data),
                    responseCallbackFunction()))
            .argument(responseListener);
  }

  private PropertyNameBuilder httpPostFunction() {
    // cant use vueName() here ;/
    return property(property("Vue", "http"), name("post"));
  }

  private NameBuilder responseCallbackFunction() {
    return name("then");
  }

}

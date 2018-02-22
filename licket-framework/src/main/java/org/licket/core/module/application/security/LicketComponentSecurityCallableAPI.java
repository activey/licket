package org.licket.core.module.application.security;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.hippo.vue.extend.AbstractVueClassCallableAPI;
import org.licket.framework.hippo.FunctionCallBuilder;

import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class LicketComponentSecurityCallableAPI extends AbstractVueClassCallableAPI {

  public LicketComponentSecurityCallableAPI(ComponentFunctionCallback functionCallback) {
    super(functionCallback);
  }

  public void setAuthenticationToken(ComponentModelProperty authenticationTokenFromModelProperty) {
    functionCallback().call(functionCall()
            .target(property(property("this", LicketComponentSecurity.serviceName()), "setAuthenticationToken"))
            .argument(authenticationTokenFromModelProperty.builder())
    );
  }
}
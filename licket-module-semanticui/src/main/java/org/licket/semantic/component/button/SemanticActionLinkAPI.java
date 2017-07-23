package org.licket.semantic.component.button;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.api.AbstractLicketComponentAPI;

import static org.licket.core.view.tree.LicketComponentTreeWalkSequence.source;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class SemanticActionLinkAPI extends AbstractLicketComponentAPI {

  public SemanticActionLinkAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback componentFunctionCallback) {
    super(licketComponent, componentFunctionCallback);
  }

  public final SemanticActionLinkAPI showLoading(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("showLoading")
            )));
    return this;
  }

  public final SemanticActionLinkAPI hideLoading(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("hideLoading")
            )));
    return this;
  }
}

package org.licket.semantic.component.dimmer;

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
public class SemanticUIDimmerAPI extends AbstractLicketComponentAPI {

  public SemanticUIDimmerAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback componentFunctionCallback) {
    super(licketComponent, componentFunctionCallback);
  }

  public final void show(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("show")
            )));

  }

  public final void hide(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("hide")
            )));
  }
}

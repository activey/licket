package org.licket.core.view.form;

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
public class AbstractLicketFormAPI extends AbstractLicketComponentAPI {

  public AbstractLicketFormAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback functionCallback) {
    super(licketComponent, functionCallback);
  }

  public final AbstractLicketFormAPI submit(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("submitForm")
            )));
    return this;
  }
}

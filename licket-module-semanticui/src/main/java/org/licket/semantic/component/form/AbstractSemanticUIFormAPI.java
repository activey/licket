package org.licket.semantic.component.form;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.form.AbstractLicketFormAPI;

import static org.licket.core.view.tree.LicketComponentTreeWalkSequence.source;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author lukaszgrabski
 */
public class AbstractSemanticUIFormAPI extends AbstractLicketFormAPI {

  public AbstractSemanticUIFormAPI(LicketComponent<?> licketComponent, ComponentFunctionCallback functionCallback) {
    super(licketComponent, functionCallback);
  }

  public final AbstractSemanticUIFormAPI showLoading(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("showLoading")
            )));
    return this;
  }

  public final AbstractSemanticUIFormAPI hideLoading(LicketComponent<?> caller) {
    functionCallback().call(functionCall().target(
            property(
                    source(caller).target(component()).traverseSequence(),
                    name("hideLoading")
            )));
    return this;
  }
}

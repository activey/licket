package org.licket.semantic.component.form;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.form.AbstractLicketForm;

/**
 * @author lukaszgrabski
 */
public abstract class AbstractSemanticUIForm<T> extends AbstractLicketForm<T> {

  public AbstractSemanticUIForm(String id, Class<T> modelClass, LicketComponentModel<T> model, LicketComponentView componentView) {
    super(id, modelClass, model, componentView);
  }

  @Override
  public AbstractSemanticUIFormAPI api(ComponentFunctionCallback functionCallback) {
    return new AbstractSemanticUIFormAPI(this, functionCallback);
  }
}

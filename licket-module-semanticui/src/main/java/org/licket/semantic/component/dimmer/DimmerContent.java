package org.licket.semantic.component.dimmer;

import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author lukaszgrabski
 */
public class DimmerContent extends AbstractLicketMonoContainer<Void> {

  public DimmerContent(String id) {
    super(id, Void.class, emptyComponentModel(), internalTemplateView());
  }
}

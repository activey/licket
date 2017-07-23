package org.licket.semantic.component.dimmer;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author lukaszgrabski
 */
public class DimmerContent extends AbstractLicketMonoContainer<Void> {

  private final LicketComponentModelReloader modelReloader;

  public DimmerContent(String id, LicketComponentModelReloader modelReloader) {
    super(id, Void.class, emptyComponentModel(), internalTemplateView());
    this.modelReloader = modelReloader;
  }

  @Override
  protected LicketComponentModelReloader getModelReloader() {
    return modelReloader;
  }
}

package org.licket.demo.view.modal;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMonoContainer;
import org.licket.core.view.container.AbstractLicketMultiContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
public class AddContactFormActionsSection extends AbstractLicketMultiContainer<Void> {

  private final LicketComponentModelReloader modelReloader;

  public AddContactFormActionsSection(String id, LicketComponentModelReloader modelReloader) {
    super(id, Void.class, emptyComponentModel(),
            fromComponentClass(AddContactFormActionsSection.class));
    this.modelReloader = modelReloader;
  }

  @Override
  protected LicketComponentModelReloader getModelReloader() {
    return modelReloader;
  }
}

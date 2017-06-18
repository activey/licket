package org.licket.demo.view.modal;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author activey
 */
public class AddContactFormModalSection extends AbstractLicketMonoContainer<Void> {

  private final LicketComponentModelReloader modelReloader;

  public AddContactFormModalSection(String id, LicketComponentModelReloader modelReloader) {
    super(id, Void.class, emptyComponentModel(),
        fromComponentClass(AddContactFormModalSection.class));
    this.modelReloader = modelReloader;
  }

  @Override
  protected LicketComponentModelReloader getModelReloader() {
    return modelReloader;
  }
}

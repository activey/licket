package org.licket.semantic.component.modal;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class ModalSection extends AbstractLicketMonoContainer<Void> {

  public ModalSection(String id, LicketComponentModelReloader modelReloader) {
    super(id, Void.class, emptyComponentModel(), internalTemplateView(), modelReloader);
  }
}

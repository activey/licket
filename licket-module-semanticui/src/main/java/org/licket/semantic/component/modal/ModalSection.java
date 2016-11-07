package org.licket.semantic.component.modal;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class ModalSection extends AbstractLicketMonoContainer<Void> {

  public ModalSection(String id, LicketComponentModelReloader modelReloader) {
    super(id, Void.class, null, internalTemplateView(), modelReloader);
  }
}

package org.licket.semantic.component.modal;

import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class ModalSection extends AbstractLicketMonoContainer<Void> {

  public ModalSection(String id) {
    super(id, Void.class, emptyComponentModel(), internalTemplateView());
  }
}

package org.licket.demo.view.modal;

import org.licket.core.view.container.AbstractLicketMultiContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
public class AddContactFormActionsSection extends AbstractLicketMultiContainer<Void> {

  public AddContactFormActionsSection(String id) {
    super(id, Void.class, emptyComponentModel(),
            fromComponentClass(AddContactFormActionsSection.class));
  }
}

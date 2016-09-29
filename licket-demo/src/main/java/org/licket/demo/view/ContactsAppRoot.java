package org.licket.demo.view;

import static org.licket.core.view.ComponentView.fromComponentContainerClass;

import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.container.LicketComponentContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ContactsAppRoot extends AbstractLicketContainer<Void> {

  public ContactsAppRoot(String id,
      @Autowired @Qualifier("contactsPanel") LicketComponentContainer contactsPanel) {
    super(id, Void.class, fromComponentContainerClass(ContactsAppRoot.class));

    add(contactsPanel);
  }
}

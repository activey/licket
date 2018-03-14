package org.licket.demo.view;

import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.demo.model.Contacts.fromIterable;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketMultiContainer<Contacts> {

  @Autowired
  private ContactsList contactsList;

  @Autowired
  private ContactsService contactsService;

  public ContactsPanel(String id) {
    super(id, Contacts.class, emptyComponentModel(), internalTemplateView());
  }

  @Override
  protected void onInitializeContainer() {
    add(contactsList);
    readContacts();
  }

  private void readContacts() {
    setComponentModelObject(fromIterable(contactsService.getAllContacts()));
  }

  public void reloadList() {
    readContacts();
  }
}

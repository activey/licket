package org.licket.demo.view;

import static org.licket.demo.model.Contacts.fromIterable;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    public ContactsPanel(String id) {
        super(id);

        add(new ContactsList("contact", () -> getComponentModel().get().getContacts()));
    }

    @Override
    protected void onInitialize() {
        readContacts();
    }

    private void readContacts() {
        setComponentModelObject(fromIterable(contactsService.getAllContacts()));
    }
}

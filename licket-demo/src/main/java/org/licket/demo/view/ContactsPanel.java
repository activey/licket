package org.licket.demo.view;

import static org.licket.core.view.ComponentContainerView.fromComponentContainerClass;
import static org.licket.demo.model.Contacts.fromIterable;

import org.licket.core.model.LicketModel;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
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
        super(id, fromComponentContainerClass(ContactsPanel.class));

        add(new ContactsList("contact", new LicketModel("contacts")));
        add(new AbstractLicketActionLink("reload") {

            @Override
            protected void onInvokeAction() {
                reloadList();
            }
        });
    }

    @Override
    protected void onInitializeContainer() {
        readContacts();
    }

    private void readContacts() {
        setComponentModelObject(fromIterable(contactsService.getAllContacts()));
    }

    public void reloadList() {
        readContacts();
    }
}

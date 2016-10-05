package org.licket.demo.view;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.demo.model.Contacts.fromIterable;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.Something;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private AddContactForm addContactForm;

    @Autowired
    private LicketRemoteCommunication remoteCommunication;

    public ContactsPanel(String id) {
        super(id, Contacts.class, fromComponentContainerClass(ContactsPanel.class));
    }

    @Override
    protected void onInitializeContainer() {
        add(addContactForm);
        add(new ContactsList("contact", new LicketModel("contacts")));
        add(new AbstractLicketActionLink("reload", remoteCommunication) {
            @Override
            protected void onInvokeAction(Something something) {
                reloadList();

                something.add(ContactsPanel.this);
            }
        });

        readContacts();
    }

    private void readContacts() {
        setComponentModel(ofModelObject(fromIterable(contactsService.getAllContacts())));
    }

    public void reloadList() {
        readContacts();
    }
}

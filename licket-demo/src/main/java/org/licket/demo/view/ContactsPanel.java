package org.licket.demo.view;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.demo.model.Contacts.fromIterable;
import org.licket.core.model.LicketModel;
import org.licket.core.view.ComponentView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    public ContactsPanel(String id, @Autowired
    @Qualifier("addContactForm") LicketComponentContainer addContactForm) {
        super(id, Contacts.class, fromComponentContainerClass(ContactsPanel.class));

        add(addContactForm);
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
        setComponentModel(ofModelObject(fromIterable(contactsService.getAllContacts())));
    }

    public void reloadList() {
        readContacts();
    }
}

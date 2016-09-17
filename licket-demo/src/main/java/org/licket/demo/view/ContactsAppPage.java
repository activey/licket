package org.licket.demo.view;

import java.util.List;
import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.demo.model.Contact;
import org.licket.demo.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.licket.core.view.ComponentContainerView.fromComponentContainerClass;

public class ContactsAppPage extends AbstractLicketPage<List<Contact>> {

    @Autowired
    @Qualifier("contactsPanel")
    private LicketComponentContainer<Contacts> contactsPanel;

    public ContactsAppPage(String id) {
        super(id, fromComponentContainerClass(ContactsAppPage.class));
    }

    @Override
    protected void onInitializeContainer() {
        add(contactsPanel);
    }
}

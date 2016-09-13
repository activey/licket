package org.licket.demo.view;

import java.util.List;
import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.demo.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.view.LicketComponentView.fromComponentClass;

public class ContactsAppPage extends AbstractLicketPage<List<Contact>> {

    @Autowired
    private ContactsPanel contactsPanel;

    public ContactsAppPage(String id) {
        super(id, fromComponentClass(ContactsAppPage.class));
    }

    @Override
    protected void onInitialize() {
        add(contactsPanel);
    }
}

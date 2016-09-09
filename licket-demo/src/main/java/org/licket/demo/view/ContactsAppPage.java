package org.licket.demo.view;

import java.util.List;
import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.demo.model.Contact;

import static org.licket.core.view.LicketComponentView.fromComponentClass;

public class ContactsAppPage extends AbstractLicketPage<List<Contact>> {

    private AbstractLicketList contactsList;

    public ContactsAppPage(String id) {
        super(id, fromComponentClass(ContactsAppPage.class));
        add(contactsList = new ContactsList());
    }

    @Override
    protected void onInitialize() {
    }
}

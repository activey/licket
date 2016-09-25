package org.licket.demo.view;

import org.licket.core.model.LicketModel;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.demo.model.Contact;
import org.licket.demo.model.EmailAddress;

import static org.licket.core.model.LicketModel.ofString;

public class ContactsList extends AbstractLicketList<Contact> {

    public ContactsList(String id, LicketModel<String> enclosingComponentPropertyModel) {
        super(id, enclosingComponentPropertyModel, Contact.class);

        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new AbstractLicketList<EmailAddress>("email", ofString("emails"), EmailAddress.class) {
            @Override
            protected void onInitializeContainer() {
                add(new LicketLabel("email"));
            }
        });
    }
}

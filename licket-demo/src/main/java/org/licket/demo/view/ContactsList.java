package org.licket.demo.view;

import static org.licket.core.model.LicketComponentModel.ofString;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.demo.model.Contact;
import org.licket.demo.model.EmailAddress;

public class ContactsList extends AbstractLicketList<Contact> {

    public ContactsList(String id, LicketComponentModel<String> enclosingComponentPropertyModel,
                        LicketComponentModelReloader modelReloader) {
        super(id, enclosingComponentPropertyModel, Contact.class, modelReloader);

        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new AbstractLicketList<EmailAddress>("email", ofString("emails"), EmailAddress.class, modelReloader) {

            @Override
            protected void onInitializeContainer() {
                add(new LicketLabel("email"));
            }
        });
    }
}

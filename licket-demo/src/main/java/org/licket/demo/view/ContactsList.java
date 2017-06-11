package org.licket.demo.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.demo.model.Contact;
import org.licket.demo.model.EmailAddress;

import static org.licket.core.model.LicketComponentModel.ofString;

public class ContactsList extends AbstractLicketList<Contact> {

    private LicketComponentModelReloader modelReloader;
    private LicketRemote licketRemote;

    public ContactsList(String id, LicketComponentModel<String> enclosingComponentPropertyModel,
                        LicketComponentModelReloader modelReloader, LicketRemote licketRemote) {
        super(id, enclosingComponentPropertyModel, Contact.class, modelReloader);
        this.modelReloader = modelReloader;
        this.licketRemote = licketRemote;
    }

    @Override
    protected void onInitializeContainer() {
        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new AbstractLicketList<EmailAddress>("email", ofString("emails"), EmailAddress.class, modelReloader) {

            @Override
            protected void onInitializeContainer() {
                add(new LicketLabel("email"));
            }
        });
        add(new MountedComponentLink<>("view-contact", licketRemote, modelReloader, ViewContactPanel.class));
    }
}

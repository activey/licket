package org.licket.demo.view;

import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.container.LicketComponentContainer;

public class ContactsAppRoot extends AbstractLicketContainer<Void> {

    public ContactsAppRoot(String id, LicketComponentContainer contactsPanel, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(ContactsAppRoot.class), modelReloader);

        add(contactsPanel);
    }
}

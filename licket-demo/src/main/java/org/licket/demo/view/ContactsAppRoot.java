package org.licket.demo.view;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.container.LicketComponentContainer;

public class ContactsAppRoot extends AbstractLicketMultiContainer<Void> {

    public ContactsAppRoot(String id, LicketComponentContainer contactsPanel, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyComponentModel(), fromComponentClass(ContactsAppRoot.class), modelReloader);

        add(contactsPanel);
    }
}

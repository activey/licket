package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

public class ContactsAppRoot extends AbstractLicketMultiContainer<Void> {

    @Autowired
    private ContactsPanel contactsPanel;

    @Autowired
    private AddContactPanel addContactPanel;

    public ContactsAppRoot(String id, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyComponentModel(), fromComponentClass(ContactsAppRoot.class), modelReloader);
    }

    @Override
    protected void onInitializeContainer() {
        addContactPanel.onContactAdded((contact, componentActionCallback) -> {
            contactsPanel.reloadList();
            componentActionCallback.reload(contactsPanel);

            return true;
        });
        add(contactsPanel);
        add(addContactPanel);
    }
}

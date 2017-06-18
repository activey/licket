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

    @Autowired
    private LicketComponentModelReloader modelReloader;

    public ContactsAppRoot(String id) {
        super(id, Void.class, emptyComponentModel(), fromComponentClass(ContactsAppRoot.class));
    }

    @Override
    protected void onInitializeContainer() {
        addContactPanel.onContactAdded((contact, componentActionCallback) -> {
            contactsPanel.reloadList();
            componentActionCallback.reload(contactsPanel);
        });
        add(contactsPanel);
        add(addContactPanel);
    }

    @Override
    protected LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }
}

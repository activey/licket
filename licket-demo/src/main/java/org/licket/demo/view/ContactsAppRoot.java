package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.mount.params.MountingParams;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

@LicketMountPoint("/")
public class ContactsAppRoot extends AbstractLicketMultiContainer<Void> {

    @Autowired
    private ContactsPanel contactsPanel;

    @Autowired
    private AddContactPanel addContactPanel;

    @Autowired
    private LicketComponentModelReloader modelReloader;

    @Autowired
    private LicketRemote remoteCommunication;

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

        add(new AbstractLicketActionLink("reload", remoteCommunication, modelReloader()) {

            protected void onClick() {
                contactsPanel.reloadList();
            }

            @Override
            protected void onAfterClick(ComponentActionCallback componentActionCallback) {
                componentActionCallback.reload(contactsPanel);
            }
        });
    }

    @Override
    protected final LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }

    @Override
    protected final void onComponentMounted(MountingParams componentMountingParams) {
        contactsPanel.reloadList();
    }

    @Override
    protected void onAfterComponentMounted(ComponentActionCallback componentActionCallback) {
        componentActionCallback.reload(contactsPanel);
    }
}

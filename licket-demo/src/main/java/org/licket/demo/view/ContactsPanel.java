package org.licket.demo.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.licket.semantic.component.modal.ModalSettings;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.demo.model.Contacts.fromIterable;
import static org.licket.semantic.component.modal.ModalSettingsBuilder.builder;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketMultiContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LicketRemote remoteCommunication;

    @Autowired
    private ContactsList contactsList;

    public ContactsPanel(String id, LicketComponentModelReloader modelReloader) {
        super(id, Contacts.class, emptyComponentModel(), internalTemplateView(), modelReloader);
    }

    @Override
    protected void onInitializeContainer() {
        add(contactsList);
        add(new AbstractLicketActionLink("reload", remoteCommunication, modelReloader()) {

            protected void onClick() {
                reloadList();
            }

            @Override
            protected void onAfterClick(ComponentActionCallback componentActionCallback) {
                componentActionCallback.reload(ContactsPanel.this);
            }
        });
        readContacts();
    }

    private void readContacts() {
        setComponentModel(ofModelObject(fromIterable(contactsService.getAllContacts())));
    }

    public void reloadList() {
        readContacts();
    }
}

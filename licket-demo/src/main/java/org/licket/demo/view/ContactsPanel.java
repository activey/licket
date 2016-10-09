package org.licket.demo.view;

import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.demo.model.Contacts.fromIterable;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LicketRemoteCommunication remoteCommunication;

    public ContactsPanel(String id, LicketComponentModelReloader modelReloader) {
        super(id, Contacts.class, emptyModel(), fromComponentContainerClass(ContactsPanel.class), modelReloader);
    }

    @Override
    protected void onInitializeContainer() {
        add(new AddContactForm("add-contact-form", contactsService, remoteCommunication, modelReloader) {
            @Override
            protected void onAfterSubmit(ComponentActionCallback componentActionCallback) {
                reloadList();
                componentActionCallback.reload(ContactsPanel.this);
            }
        });
        add(new ContactsList("contact", new LicketModel("contacts"), modelReloader));
        add(new AbstractLicketActionLink("reload", remoteCommunication, modelReloader) {

            protected void onInvokeAction() {
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

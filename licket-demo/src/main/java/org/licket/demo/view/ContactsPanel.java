package org.licket.demo.view;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.demo.model.Contacts.fromIterable;
import static org.licket.semantic.component.modal.ModalSettingsBuilder.builder;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.container.LicketInlineContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.link.ComponentFunctionCallback;
import org.licket.demo.model.Contacts;
import org.licket.demo.service.ContactsService;
import org.licket.demo.view.modal.AddContactFormModalSection;
import org.licket.semantic.component.modal.AbstractSemanticUIModal;
import org.licket.semantic.component.modal.ModalSection;
import org.licket.semantic.component.modal.ModalSettings;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class ContactsPanel extends AbstractLicketMultiContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LicketRemote remoteCommunication;

    private LicketComponentModelReloader modelReloader;

    public ContactsPanel(String id, LicketComponentModelReloader modelReloader) {
        super(id, Contacts.class, emptyComponentModel(), internalTemplateView(), modelReloader);
        this.modelReloader = modelReloader;
    }

    @Override
    protected void onInitializeContainer() {
        add(new ContactsList("contact", new LicketComponentModel("contacts"), modelReloader));
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

    private ModalSettings modalSettings() {
        return builder().showActions().build();
    }

    private void readContacts() {
        setComponentModel(ofModelObject(fromIterable(contactsService.getAllContacts())));
    }

    public void reloadList() {
        readContacts();
    }
}

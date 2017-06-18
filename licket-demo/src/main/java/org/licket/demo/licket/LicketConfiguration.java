package org.licket.demo.licket;

import org.licket.core.model.LicketComponentModel;
import org.licket.demo.view.AddContactForm;
import org.licket.demo.view.AddContactPanel;
import org.licket.demo.view.ContactsAppRoot;
import org.licket.demo.view.ContactsList;
import org.licket.demo.view.ContactsPanel;
import org.licket.demo.view.ViewContactPanel;
import org.licket.semantic.SemanticUIPluginConfiguration;
import org.licket.semantic.component.modal.ModalSettings;
import org.licket.spring.annotation.LicketComponent;
import org.licket.spring.annotation.LicketRootContainer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.licket.semantic.component.modal.ModalSettingsBuilder.builder;

@Configuration
@Import(SemanticUIPluginConfiguration.class)
public class LicketConfiguration {

    @LicketRootContainer
    public ContactsAppRoot root() {
        return new ContactsAppRoot("contacts-page");
    }

    @LicketComponent
    public ContactsPanel contactsPanel() {
        return new ContactsPanel("contacts-panel");
    }

    @LicketComponent
    public ContactsList contactsList() {
        return new ContactsList("contact", new LicketComponentModel("contacts"));
    }

    @LicketComponent
    public AddContactForm addContactForm() {
        return new AddContactForm("new-contact-form");
    }

    @LicketComponent
    public ViewContactPanel viewContactPanel() {
        return new ViewContactPanel("view-contact-panel");
    }

    private ModalSettings modalDialogSettings() {
        return builder().showActions().build();
    }

    @LicketComponent
    public AddContactPanel addContactPanel() {
        return new AddContactPanel("add-contact-panel", modalDialogSettings());
    }
}

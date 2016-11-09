package org.licket.demo.licket;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.demo.view.AddContactPanel;
import org.licket.demo.view.ContactsAppRoot;
import org.licket.demo.view.ContactsPanel;
import org.licket.semantic.SemanticUIPluginConfiguration;
import org.licket.semantic.component.modal.ModalSettings;
import org.licket.semantic.component.modal.ModalSettingsBuilder;
import org.licket.spring.annotation.LicketComponent;
import org.licket.spring.annotation.LicketRootContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.licket.semantic.component.modal.ModalSettingsBuilder.builder;

@Configuration
@Import(SemanticUIPluginConfiguration.class)
public class LicketConfiguration {

    @LicketRootContainer
    public ContactsAppRoot root(@Autowired LicketComponentModelReloader modelReloader) {
        return new ContactsAppRoot("contacts-page", modelReloader);
    }

    @LicketComponent
    public ContactsPanel contactsPanel(@Autowired LicketComponentModelReloader modelReloader) {
        return new ContactsPanel("contacts-panel", modelReloader);
    }

    private ModalSettings modalDialogSettings() {
        return builder().build();
    }

    @LicketComponent
    public AddContactPanel addContactPanel(@Autowired LicketComponentModelReloader modelReloader) {
        return new AddContactPanel("add-contact-panel", modelReloader, modalDialogSettings());
    }
}

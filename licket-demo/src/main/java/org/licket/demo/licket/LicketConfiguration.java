package org.licket.demo.licket;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.demo.view.ContactsAppRoot;
import org.licket.demo.view.ContactsPanel;
import org.licket.semantic.SemanticUIModuleConfiguration;
import org.licket.spring.annotation.LicketComponent;
import org.licket.spring.annotation.LicketRootContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SemanticUIModuleConfiguration.class)
public class LicketConfiguration {

    @LicketRootContainer
    public ContactsAppRoot root(@Autowired LicketComponentModelReloader modelReloader) {
        return new ContactsAppRoot("contacts-page", contactsPanel(modelReloader), modelReloader);
    }

    @LicketComponent
    public ContactsPanel contactsPanel(LicketComponentModelReloader modelReloader) {
        return new ContactsPanel("contacts-panel", modelReloader);
    }
}

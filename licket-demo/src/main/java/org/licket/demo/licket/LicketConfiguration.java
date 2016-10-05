package org.licket.demo.licket;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.demo.view.AddContactForm;
import org.licket.demo.view.ContactsAppRoot;
import org.licket.demo.view.ContactsPanel;
import org.licket.demo.view.semantic.JqueryLibraryResource;
import org.licket.demo.view.semantic.SemanticLibraryResource;
import org.licket.demo.view.semantic.SemanticStylesheetResource;
import org.licket.spring.annotation.LicketComponent;
import org.licket.spring.annotation.LicketRootContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
public class LicketConfiguration {

    @LicketRootContainer
    public LicketComponentContainer root() {
        return new ContactsAppRoot("contacts-page", contactsPanel());
    }

    @LicketComponent
    public LicketComponentContainer contactsPanel() {
        return new ContactsPanel("contacts-panel");
    }

    @LicketComponent
    public AddContactForm addContactForm() {
        return new AddContactForm("add-contact-form");
    }

    @Bean
    @Order(10)
    public HeadParticipatingResource jqueryResource() {
        return new JqueryLibraryResource();
    }

    @Bean
    @Order(11)
    public HeadParticipatingResource semanticLibrary() {
        return new SemanticLibraryResource();
    }

    @Bean
    @Order(12)
    public HeadParticipatingResource semanticStylesheet() {
        return new SemanticStylesheetResource();
    }
}

package org.licket.demo.licket;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.demo.view.ContactsAppRoot;
import org.licket.demo.view.ContactsPanel;
import org.licket.demo.view.semantic.JqueryLibraryResource;
import org.licket.demo.view.semantic.SemanticLibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class LicketConfiguration {

    @Bean(name = "root")
    @SessionScope
    public LicketComponentContainer root() {
        return new ContactsAppRoot("contacts-page");
    }

    @Bean(name = "contactsPanel")
    @SessionScope
    public LicketComponentContainer contactsPanel() {
        return new ContactsPanel("contacts");
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
}

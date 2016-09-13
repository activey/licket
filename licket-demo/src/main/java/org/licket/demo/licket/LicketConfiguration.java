package org.licket.demo.licket;

import org.licket.core.LicketApplicationBuilder;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.LicketComponent;
import org.licket.demo.model.Contact;
import org.licket.demo.view.ContactsAppPage;
import org.licket.demo.view.ContactsList;
import org.licket.demo.view.ContactsPanel;
import org.licket.demo.view.semantic.SemanticLibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Configuration
public class LicketConfiguration {

    @Bean
    public LicketApplicationBuilder applicationBuilder() {
        return LicketApplicationBuilder.applicationBuilder().name("Demo application");
    }

    @Bean
    @SessionScope
    public AbstractLicketPage<?> mainPage() {
        return new ContactsAppPage("contacts-page");
    }

    @Bean
    @SessionScope
    public ContactsPanel contactsPanel() {
        return new ContactsPanel("contacts");
    }

    @Bean
    public HeadParticipatingResource semanticLibrary() {
        return new SemanticLibraryResource();
    }
}

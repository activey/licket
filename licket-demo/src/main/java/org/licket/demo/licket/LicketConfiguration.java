package org.licket.demo.licket;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.demo.view.ContactsAppPage;
import org.licket.demo.view.ContactsPanel;
import org.licket.demo.view.semantic.SemanticLibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class LicketConfiguration {

    @Bean(name = "root")
    @SessionScope
    public LicketComponentContainer root() {
        return new ContactsAppPage("contacts-page");
    }

    @Bean
    @SessionScope
    public LicketComponentContainer contactsPanel() {
        return new ContactsPanel("contacts");
    }

    @Bean
    public HeadParticipatingResource semanticLibrary() {
        return new SemanticLibraryResource();
    }
}

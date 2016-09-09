package org.licket.demo.licket;

import org.licket.core.LicketApplicationBuilder;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.demo.view.ContactsAppPage;
import org.licket.demo.view.semantic.SemanticLibraryResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class LicketConfiguration {

    @Bean
    public LicketApplicationBuilder applicationBuilder() {
        return LicketApplicationBuilder.applicationBuilder().name("Demo application");
    }

    @Bean
    @SessionScope
    public ContactsAppPage mainPage() {
        return new ContactsAppPage("contacts");
    }

    @Bean
    public HeadParticipatingResource semanticLibrary() {
        return new SemanticLibraryResource();
    }
}

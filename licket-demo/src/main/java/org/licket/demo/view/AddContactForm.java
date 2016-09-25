package org.licket.demo.view;

import com.github.javafaker.Faker;
import org.licket.core.view.form.AbstractLicketForm;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.AbstractLicketFormSubmitButton;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentContainerView.fromComponentContainerClass;

/**
 * @author activey
 */
public class AddContactForm extends AbstractLicketForm<Contact> {

    private Faker faker = new Faker();

    @Autowired
    private ContactsService contactsService;

    public AddContactForm(String id) {
        super(id, Contact.class, fromComponentContainerClass(AddContactForm.class), ofModelObject(new Contact()));

        add(new LicketInput("name"));
        add(new LicketInput("description"));
        add(new AbstractLicketActionLink("add") {

            @Override
            protected void onInvokeAction() {
                contactsService.addContact(new Contact(faker.name().fullName(), faker.lorem().paragraph()));
            }
        });

        add(new AbstractLicketFormSubmitButton<>("add", Contact.class));
    }

    @Override
    protected void onInvokeAction(Contact componentModel) {
        contactsService.addContact(new Contact(faker.name().fullName(), faker.lorem().paragraph()));
    }
}

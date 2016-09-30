package org.licket.demo.view;

import com.github.javafaker.Faker;
import org.licket.core.module.forms.component.AbstractLicketForm;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.link.LicketFormSubmitButton;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;

/**
 * @author activey
 */
public class AddContactForm extends AbstractLicketForm<Contact> {

    private Faker faker = new Faker();

    @Autowired
    private ContactsService contactsService;

    public AddContactForm(String id) {
        super(id, Contact.class, ofModelObject(new Contact()), fromComponentContainerClass(AddContactForm.class));

        add(new LicketInput("name"));
        add(new LicketInput("description"));
//        add(new LicketActionLink("add") {
//
//            @Override
//            protected void onInvokeAction() {
//                contactsService.addContact(new Contact(faker.name().fullName(), faker.lorem().paragraph()));
//            }
//        });

        add(new LicketFormSubmitButton("add"));
    }

    @Override
    protected void onSubmit() {
        contactsService.addContact(getComponentModel().get());
    }
}

package org.licket.demo.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.module.forms.component.AbstractLicketForm;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.link.LicketFormSubmitButton;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;

/**
 * @author activey
 */
public class AddContactForm extends AbstractLicketForm<Contact> {

    private ContactsService contactsService;

    public AddContactForm(String id, ContactsService contactsService, LicketRemoteCommunication remoteCommunication,
                          LicketComponentModelReloader modelReloader) {
        super(id, Contact.class, ofModelObject(new Contact()), fromComponentContainerClass(AddContactForm.class),
            remoteCommunication, modelReloader);
        this.contactsService = checkNotNull(contactsService, "Contacts service has to be not null!");

        add(new LicketInput("name"));
        add(new LicketInput("description"));
        add(new LicketFormSubmitButton("add"));
    }

    @Override
    protected void onSubmit() {
        contactsService.addContact(getComponentModel().get());

        clearInput();
    }

    private void clearInput() {
        setComponentModelObject(new Contact());
    }
}

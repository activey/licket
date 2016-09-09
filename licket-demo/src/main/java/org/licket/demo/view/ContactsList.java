package org.licket.demo.view;

import org.licket.core.model.LicketModel;
import org.licket.core.resource.image.ImageResource;
import org.licket.core.resource.image.ImageType;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.media.LicketImage;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.resource.image.ImageResource.fromClasspath;
import static org.licket.core.resource.image.ImageType.JPEG;

public class ContactsList extends AbstractLicketList<Contact> {

    @Autowired
    private ContactsService contactsService;

    public ContactsList(String id, LicketModel<Iterable<Contact>> listModel) {
        super(id, listModel);
    }

    public ContactsList(String id) {
        super(id);
    }

    public ContactsList() {
        super("contacts-list");

        add(new LicketImage("contact-picture-url", fromClasspath("test.jpg", JPEG)));
        add(new LicketLabel("contact-name"));
        add(new LicketLabel("contact-description"));
    }

    @Override
    protected void onInitialize() {
        readContacts();
    }

    private void readContacts() {
        setComponentModelObject(contactsService.getAllContacts());
    }
}

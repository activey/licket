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

import java.util.function.Supplier;

import static org.licket.core.resource.image.ImageResource.fromClasspath;
import static org.licket.core.resource.image.ImageType.JPEG;

public class ContactsList extends AbstractLicketList<Contact> {

    public ContactsList(String id, LicketModel<String> enclosingComponentPropertyModel) {
        super(id, enclosingComponentPropertyModel, Contact.class);

        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
//        add(new LicketImage("pictureUrl", fromClasspath("test.jpg", JPEG)));
    }
}

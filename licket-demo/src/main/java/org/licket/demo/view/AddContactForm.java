package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.form.AbstractLicketForm;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.BiConsumer;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author activey
 */
public class AddContactForm extends AbstractLicketForm<Contact> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LicketComponentModelReloader modelReloader;

    @Autowired
    private LicketRemote remote;

    private BiConsumer<Contact, ComponentActionCallback> callback;

    public AddContactForm(String id) {
        super(id, Contact.class, ofModelObject(new Contact()), internalTemplateView());
    }

    public final void onContactAdded(BiConsumer<Contact, ComponentActionCallback> callback) {
        this.callback = callback;
    }

    @Override
    protected void onInitializeContainer() {
        add(new LicketInput("name"));
        add(new LicketInput("description"));
    }

    @Override
    protected void onSubmit() {
        contactsService.addContact(getComponentModel().get());
        clearInput();
    }

    private void clearInput() {
        setComponentModelObject(new Contact());
    }

    @Override
    protected LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }

    @Override
    protected final LicketRemote getRemote() {
        return remote;
    }

    @Override
    protected final void onAfterSubmit(ComponentActionCallback componentActionCallback) {
        if (callback == null) {
            return;
        }
        callback.accept(getComponentModel().get(), componentActionCallback);
    }
}

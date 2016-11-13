package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.container.LicketInlineContainer;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.link.ComponentFunctionCallback;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.licket.demo.view.modal.AddContactFormModalSection;
import org.licket.semantic.component.modal.AbstractSemanticUIModal;
import org.licket.semantic.component.modal.ModalSection;
import org.licket.semantic.component.modal.ModalSettings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class AddContactPanel extends AbstractLicketMultiContainer<Void> {

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private LicketRemote remoteCommunication;

    private final ModalSettings modalSettings;
    private AbstractSemanticUIModal modal;
    private BiPredicate<Contact, ComponentActionCallback> callback;

    public AddContactPanel(String id, LicketComponentModelReloader modelReloader, ModalSettings modalSettings) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView(), modelReloader);
        this.modalSettings = modalSettings;
    }

    public final void onContactAdded(BiPredicate<Contact, ComponentActionCallback> callback) {
        this.callback = callback;
    }

    @Override
    protected void onInitializeContainer() {
        add(new AbstractLicketLink("add-contact") {
            @Override
            protected void onClick(ComponentFunctionCallback callback) {
                callback.call(modal.callShow(this));
            }
        });

        add(modal = new AbstractSemanticUIModal("form-modal", modalSettings, modelReloader()) {

            @Override
            protected void onInitializeBody(ModalSection bodySection, String contentId) {
                bodySection.add(new AddContactFormModalSection(contentId, modelReloader()) {
                    @Override
                    protected void onInitializeContainer() {
                        add(new AddContactForm("new-contact-form", contactsService, remoteCommunication, modelReloader()) {

                            @Override
                            protected void onAfterSubmit(ComponentActionCallback componentActionCallback) {
                                if (callback != null) {
                                    if (callback.test(getComponentModel().get(), componentActionCallback)) {
                                        componentActionCallback.call(modal.callHide(this));
                                    }
                                    return;
                                }
                                componentActionCallback.call(modal.callHide(this));
                            }
                        });
                    }
                });
            }

            @Override
            protected void onInitializeActions(ModalSection content, String contentId) {
                content.add(new LicketInlineContainer<Void>(contentId, Void.class, modelReloader()) {
                    // TODO not yet implemented
                });
            }
        });

    }
}

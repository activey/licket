package org.licket.demo.view;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.demo.model.Contact;
import org.licket.demo.view.modal.AddContactFormActionsSection;
import org.licket.demo.view.modal.AddContactFormModalSection;
import org.licket.semantic.component.modal.AbstractSemanticUIModal;
import org.licket.semantic.component.modal.ModalSection;
import org.licket.semantic.component.modal.ModalSettings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.BiConsumer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class AddContactPanel extends AbstractLicketMultiContainer<Void> {

    @Autowired
    private AddContactForm addContactForm;

    @Autowired
    private LicketRemote remoteCommunication;

    @Autowired
    private LicketComponentModelReloader modelReloader;

    private AbstractSemanticUIModal modal;
    private final ModalSettings modalSettings;

    public AddContactPanel(String id, ModalSettings modalSettings) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView());
        this.modalSettings = modalSettings;
    }

    public final void onContactAdded(BiConsumer<Contact, ComponentActionCallback> callbackConsumer) {
        addContactForm.onContactAdded((contact, componentActionCallback) -> {
            modal.api(componentActionCallback).hide(addContactForm);
            callbackConsumer.accept(contact, componentActionCallback);
        });
    }

    @Override
    protected void onInitializeContainer() {
        add(modal = new AbstractSemanticUIModal("form-modal", modalSettings, modelReloader()) {

            @Override
            protected void onInitializeBody(ModalSection bodySection, String contentId) {
                bodySection.add(new AddContactFormModalSection(contentId, modelReloader()) {
                    @Override
                    protected void onInitializeContainer() {
                        add(addContactForm);
                    }
                });
            }

            @Override
            protected void onInitializeActions(ModalSection actionsSection, String contentId) {
                actionsSection.add(new AddContactFormActionsSection(contentId, modelReloader()) {
                    @Override
                    protected void onInitializeContainer() {
                        add(new AbstractLicketLink("add") {
                          @Override
                          protected void onClick(ComponentFunctionCallback callback) {
                            addContactForm.api(callback).submit(this);
                          }
                        });

                        add(new AbstractLicketActionLink("add_random", remoteCommunication, modelReloader()) {
                            @Override
                            protected void onClick() {
                                addContactForm.generateRandomData();
                            }

                            @Override
                            protected void onAfterClick(ComponentActionCallback componentActionCallback) {
                                componentActionCallback.reload(addContactForm);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public final LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }

    public final void showAddContactModal(ComponentFunctionCallback componentActionCallback, LicketComponent<?> caller) {
        modal.api(componentActionCallback).show(caller);
    }
}

package org.licket.demo.view;

import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.link.AbstractLicketActionLink;
import org.licket.core.view.link.AbstractLicketLink;
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

    private AbstractSemanticUIModal modal;
    private final ModalSettings modalSettings;

    public AddContactPanel(String id, ModalSettings modalSettings) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView());
        this.modalSettings = modalSettings;
    }

    public final void onContactAdded(BiConsumer<Contact, ComponentActionCallback> callbackConsumer) {
        addContactForm.onContactAdded((contact, componentActionCallback) -> {
            modal.api(componentActionCallback).hide(addContactForm);
            addContactForm.api(componentActionCallback).hideLoading(addContactForm);

            callbackConsumer.accept(contact, componentActionCallback);
        });
    }

    @Override
    protected void onInitializeContainer() {
        add(modal = new AbstractSemanticUIModal("form-modal", modalSettings) {

            @Override
            protected void onInitializeBody(ModalSection bodySection, String contentId) {
                bodySection.add(new AddContactFormModalSection(contentId) {
                    @Override
                    protected void onInitializeContainer() {
                        add(addContactForm);
                    }
                });
            }

            @Override
            protected void onInitializeActions(ModalSection actionsSection, String contentId) {
                actionsSection.add(new AddContactFormActionsSection(contentId) {
                    @Override
                    protected void onInitializeContainer() {
                        add(new AbstractLicketLink("add") {
                          @Override
                          protected void onClick(ComponentFunctionCallback callback) {
                            addContactForm
                                    .api(callback)
                                    .showLoading(this)
                                    .submit(this);
                          }
                        });

                        add(new AbstractLicketActionLink<Void>("add_email", Void.class) {

                            @Override
                            protected void onBeforeClick(ComponentFunctionCallback componentFunctionCallback) {
                                addContactForm.api(componentFunctionCallback).showLoading(this);
                            }

                            @Override
                            protected void onClick(Void modelObject) {
                                addContactForm.addEmail();
                            }

                            @Override
                            protected void onAfterClick(ComponentActionCallback componentActionCallback) {
                                componentActionCallback.patch(addContactForm);
                                addContactForm.api(componentActionCallback).hideLoading(this);
                            }
                        });

                        add(new AbstractLicketActionLink<Void>("add_random", Void.class) {

                            @Override
                            protected void onBeforeClick(ComponentFunctionCallback componentFunctionCallback) {
                                addContactForm.api(componentFunctionCallback).showLoading(this);
                            }

                            @Override
                            protected void onClick(Void modelObject) {
                                addContactForm.generateRandomData();
                            }

                            @Override
                            protected void onAfterClick(ComponentActionCallback componentActionCallback) {
                                componentActionCallback.reload(addContactForm);
                                addContactForm.api(componentActionCallback).hideLoading(this);
                            }
                        });
                    }
                });
            }
        });
    }

    public final void showAddContactModal(ComponentFunctionCallback componentActionCallback, LicketComponent<?> caller) {
        modal.api(componentActionCallback).show(caller);
    }
}

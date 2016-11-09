package org.licket.semantic.component.modal;

/**
 * @author grabslu
 */
public class ModalSettingsBuilder {

    private final ModalSettings modalSettings;

    private ModalSettingsBuilder() {
        this.modalSettings = new ModalSettings();
    }

    public static ModalSettingsBuilder builder() {
        return new ModalSettingsBuilder();
    }

    public final ModalSettingsBuilder showActions() {
        modalSettings.setShowActions(true);
        return this;
    }

    public final ModalSettings build() {
        return modalSettings;
    }
}

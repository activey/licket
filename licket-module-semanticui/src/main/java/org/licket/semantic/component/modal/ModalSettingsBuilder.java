package org.licket.semantic.component.modal;

/**
 * @author grabslu
 */
public class ModalSettingsBuilder {

    public final ModalSettings build() {
        return new ModalSettings();
    }

    private ModalSettingsBuilder() {}

    public static ModalSettingsBuilder builder() {
        return new ModalSettingsBuilder();
    }
}

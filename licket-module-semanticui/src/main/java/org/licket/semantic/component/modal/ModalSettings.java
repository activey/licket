package org.licket.semantic.component.modal;

/**
 * @author grabslu
 */
public class ModalSettings {

    private boolean showActions;
    private boolean closeable;

    public boolean isShowActions() {
        return showActions;
    }

    public void setShowActions(boolean showActions) {
        this.showActions = showActions;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }
}

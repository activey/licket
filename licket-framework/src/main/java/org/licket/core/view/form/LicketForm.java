package org.licket.core.view.form;

import org.licket.core.view.ComponentContainerView;
import org.licket.core.view.container.AbstractLicketContainer;

/**
 * @author activey
 */
public class LicketForm<T> extends AbstractLicketContainer<T> {

    public LicketForm(String id, ComponentContainerView componentView) {
        super(id, componentView);
    }

    @Override
    protected void onInvokeAction() {
        super.onInvokeAction();
    }
}

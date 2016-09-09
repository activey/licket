package org.licket.core.view;

import org.licket.core.model.LicketModel;

public abstract class AbstractLicketPage<T> extends AbstractLicketComponent<T> {

    public AbstractLicketPage(String id) {
        super(id);
    }

    public AbstractLicketPage(String id, LicketComponentView componentView) {
        super(id, componentView);
    }

    public AbstractLicketPage(String id, LicketComponentView componentView, LicketModel<T> componentModel) {
        super(id, componentView, componentModel);
    }
}

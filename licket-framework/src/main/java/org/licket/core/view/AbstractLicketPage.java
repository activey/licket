package org.licket.core.view;

import org.licket.core.model.LicketModel;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.render.ComponentRenderingContext;

public abstract class AbstractLicketPage<T> extends AbstractLicketContainer<T> {

    public AbstractLicketPage(String id, ComponentContainerView componentView) {
        super(id, componentView);
    }

    public AbstractLicketPage(String id, ComponentContainerView componentView, LicketModel<T> componentModel) {
        super(id, componentView, componentModel);
    }
}

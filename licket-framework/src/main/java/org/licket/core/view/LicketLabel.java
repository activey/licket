package org.licket.core.view;

import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;

import static org.licket.core.view.LicketComponentView.fromCurrentMarkup;

public class LicketLabel extends AbstractLicketComponent<String> {

    public LicketLabel(String id) {
        super(id);
    }

    public LicketLabel(String id, LicketModel<String> labelModel) {
        // TODO 
        super(id, fromCurrentMarkup(), labelModel);
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {

    }
}

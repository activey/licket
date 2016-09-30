package org.licket.core.defaults;

import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.container.ExternalizedComponentView;

import static org.licket.core.model.LicketModel.emptyModel;

/**
 * @author activey
 */
public class DefaultApplicationRoot extends AbstractLicketContainer<Void> {

    public DefaultApplicationRoot(String id, ExternalizedComponentView componentView) {
        super(id, Void.class, emptyModel(), componentView);
    }
}

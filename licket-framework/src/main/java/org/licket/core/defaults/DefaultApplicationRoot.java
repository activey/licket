package org.licket.core.defaults;

import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.container.ExternalizedComponentContainerView;

import static org.licket.core.model.LicketModel.empty;

/**
 * @author activey
 */
public class DefaultApplicationRoot extends AbstractLicketContainer<Object> {

    public DefaultApplicationRoot(String id, ExternalizedComponentContainerView componentView) {
        super(id, componentView, empty());
    }
}

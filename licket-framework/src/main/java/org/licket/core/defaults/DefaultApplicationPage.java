package org.licket.core.defaults;

import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.container.ExternalizedComponentContainerView;

import static org.licket.core.model.LicketModel.empty;

/**
 * @author activey
 */
public class DefaultApplicationPage extends AbstractLicketPage<Object> {

    public DefaultApplicationPage(String id, ExternalizedComponentContainerView componentView) {
        super(id, componentView, empty());
    }
}

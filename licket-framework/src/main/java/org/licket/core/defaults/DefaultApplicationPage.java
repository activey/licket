package org.licket.core.defaults;

import org.licket.core.view.AbstractLicketPage;
import org.licket.core.view.LicketComponentView;

import static org.licket.core.model.LicketModel.empty;

/**
 * @author activey
 */
public class DefaultApplicationPage extends AbstractLicketPage<Object> {

    public DefaultApplicationPage(String id, LicketComponentView componentView) {
        super(id, componentView, empty());
    }
}

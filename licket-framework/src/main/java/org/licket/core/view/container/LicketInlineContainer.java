package org.licket.core.view.container;

import org.licket.core.module.application.LicketComponentModelReloader;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author grabslu
 */
public class LicketInlineContainer<T> extends AbstractLicketMonoContainer<T> {

    public LicketInlineContainer(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass, emptyComponentModel(), internalTemplateView(), modelReloader);
    }
}

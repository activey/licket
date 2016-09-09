package org.licket.core.view.media;

import org.licket.core.resource.image.ImageResource;
import org.licket.core.view.AbstractLicketComponent;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromCurrentMarkup;

public class LicketImage extends AbstractLicketComponent<ImageResource> {

    public LicketImage(String id) {
        super(id);
    }

    public LicketImage(String id, ImageResource imageResource) {
        super(id, fromCurrentMarkup(), ofModelObject(imageResource));
    }
}

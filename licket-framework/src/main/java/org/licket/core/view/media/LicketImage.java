package org.licket.core.view.media;

import org.licket.core.resource.image.ImageResource;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

import static org.licket.core.model.LicketModel.ofModelObject;

public class LicketImage extends AbstractLicketComponent<ImageResource> {

    public LicketImage(String id) {
        super(id);
    }

    public LicketImage(String id, ImageResource imageResource) {
        super(id, ofModelObject(imageResource));
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
    }
}

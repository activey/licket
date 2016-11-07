package org.licket.core.view.media;

import org.licket.core.resource.image.ImageResource;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

import static org.licket.core.model.LicketComponentModel.ofModelObject;

public class LicketImage extends AbstractLicketComponent<ImageResource> {

    public LicketImage(String id) {
        super(id, ImageResource.class);
    }

    public LicketImage(String id, ImageResource imageResource) {
        super(id, ImageResource.class, ofModelObject(imageResource));
    }

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
    }
}

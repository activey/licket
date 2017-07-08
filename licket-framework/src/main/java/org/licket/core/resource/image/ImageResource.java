package org.licket.core.resource.image;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author activey
 */
public class ImageResource extends AbstractClasspathResource {

    public ImageResource(String classpathLocation, ImageType imageType) {
        super(classpathLocation, imageType.getMimetype());
    }

    @Override
    public String getName() {
        return null;
    }
}

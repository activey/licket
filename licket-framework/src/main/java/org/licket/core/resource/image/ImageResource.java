package org.licket.core.resource.image;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author activey
 */
public class ImageResource extends AbstractClasspathResource {

    public static ImageResource fromClasspath(String classpathLocation, ImageType imageType) {
        return new ImageResource(classpathLocation, imageType);
    }

    public ImageResource(String classpathLocation, ImageType imageType) {
        super(classpathLocation, imageType.getMimetype());
    }

    @Override
    public String getName() {
        return null;
    }
}

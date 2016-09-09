package org.licket.core.resource.image;

/**
 * @author activey
 */
public enum ImageType {

    JPEG("image/jpeg");

    private final String mimetype;

    ImageType(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getMimetype() {
        return mimetype;
    }
}

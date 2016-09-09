package org.licket.core.resource;

import java.io.InputStream;

/**
 * @author activey
 */
public abstract class AbstractClasspathResource implements Resource {

    private final String classpathLocation;
    private final String mimetype;

    public AbstractClasspathResource(String classpathLocation, String mimetype) {
        this.classpathLocation = classpathLocation;
        this.mimetype = mimetype;
    }

    @Override
    public final InputStream getStream() {
        return AbstractClasspathResource.class.getClassLoader().getResourceAsStream(classpathLocation);
    }

    @Override
    public final String getMimeType() {
        return mimetype;
    }
}

package org.licket.core.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author activey
 */
public class InputStreamResource implements Resource {

    private final String name;
    private final String mimetype;
    private InputStream inputStream;

    public InputStreamResource(String name, String mimetype, InputStream inputStream) {
        this.name = name;
        this.mimetype = mimetype;
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getStream() {
        return inputStream;
    }

    @Override
    public String getMimeType() {
        return mimetype;
    }

    @Override
    public String getName() {
        return name;
    }
}

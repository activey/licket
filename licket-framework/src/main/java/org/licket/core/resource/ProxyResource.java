package org.licket.core.resource;

import java.io.InputStream;

/**
 * @author activey
 */
public class ProxyResource implements Resource {

    private Resource proxied;
    private String name;

    public ProxyResource(Resource proxied, String name) {
        this.proxied = proxied;
        this.name = name;
    }

    @Override
    public InputStream getStream() {
        return proxied.getStream();
    }

    @Override
    public String getMimeType() {
        return proxied.getMimeType();
    }

    @Override
    public String getName() {
        return name;
    }
}

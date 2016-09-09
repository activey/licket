package org.licket.core.resource;

import java.io.InputStream;

/**
 * @author activey
 */
public interface Resource {

    InputStream getStream();

    String getMimeType();

    String getName();
}

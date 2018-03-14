package org.licket.core.resource;

import java.io.InputStream;

/**
 * @author activey
 */
public interface Resource extends ResourceHeader {

  InputStream getStream();
}

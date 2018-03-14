package org.licket.core.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author activey
 */
public class ByteArrayResource implements Resource {

  private final String name;
  private final String mimetype;
  private byte[] byteArray;

  public ByteArrayResource(String name, String mimetype, byte[] byteArray) {
    this.name = name;
    this.mimetype = mimetype;
    this.byteArray = byteArray;
  }

  @Override
  public InputStream getStream() {
    return new ByteArrayInputStream(byteArray);
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

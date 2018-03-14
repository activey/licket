package org.licket.core.resource.font;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author grabslu
 */
public class WoffFontResource extends AbstractClasspathResource {

  public static final String FONT_MIMETYPE = "application/font-woff2";
  private String name;

  public WoffFontResource(String name, String classpathLocation) {
    super(classpathLocation, FONT_MIMETYPE);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}

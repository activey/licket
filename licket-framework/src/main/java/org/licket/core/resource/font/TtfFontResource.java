package org.licket.core.resource.font;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author grabslu
 */
public class TtfFontResource extends AbstractClasspathResource {

  public static final String FONT_MIMETYPE = "application/x-font-ttf";
  private String name;

  public TtfFontResource(String name, String classpathLocation) {
    super(classpathLocation, FONT_MIMETYPE);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}

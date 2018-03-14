package org.licket.core.resource.css;

import com.google.common.net.MediaType;
import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author grabslu
 */
public class StylesheetResource extends AbstractClasspathResource {

  private String name;

  public StylesheetResource(String name, String classpathLocation) {
    super(classpathLocation, MediaType.CSS_UTF_8.toString());
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}

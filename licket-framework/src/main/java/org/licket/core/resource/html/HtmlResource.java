package org.licket.core.resource.html;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author activey
 */
public class HtmlResource extends AbstractClasspathResource {

  public static final String HTML_MIMETYPE = "text/html";
  private final String name;

  public HtmlResource(String name, String classpathLocation) {
    super(classpathLocation, HTML_MIMETYPE);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}

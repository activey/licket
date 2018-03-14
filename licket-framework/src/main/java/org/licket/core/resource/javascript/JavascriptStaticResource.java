package org.licket.core.resource.javascript;

import org.licket.core.resource.AbstractClasspathResource;

import static com.google.common.net.MediaType.JAVASCRIPT_UTF_8;

/**
 * @author activey
 */
public class JavascriptStaticResource extends AbstractClasspathResource {

  private final String name;

  public JavascriptStaticResource(String name, String classpathLocation) {
    super(classpathLocation, JAVASCRIPT_UTF_8.toString());
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}

package org.licket.spring.security.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class TinyCookieResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public TinyCookieResource() {
    super("tony-cookie.js", "tiny-cookie/tiny-cookie.js");
  }
}

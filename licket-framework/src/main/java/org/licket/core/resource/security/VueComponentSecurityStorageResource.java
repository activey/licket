package org.licket.core.resource.security;

import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author lukaszgrabski
 */
public class VueComponentSecurityStorageResource extends JavascriptStaticResource implements FootParticipatingResource {

  public VueComponentSecurityStorageResource() {
    super("Licket.security.storage.js", "security/Licket.security.storage.js");
  }
}

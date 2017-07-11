package org.licket.core.resource.patch;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author lukaszgrabski
 */
public class FastJsonPatchResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public FastJsonPatchResource() {
    super("fast-json-patch.min.js", "patch/fast-json-patch.min.js");
  }
}

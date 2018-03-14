package org.licket.core.resource.vue;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class VueRouterLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public VueRouterLibraryResource() {
    super("vue-router.js", "vue-router/dist/vue-router.js");
  }
}

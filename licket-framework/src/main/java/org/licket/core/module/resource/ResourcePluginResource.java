package org.licket.core.module.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class ResourcePluginResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public ResourcePluginResource() {
    super("vue-resource.js", "vue-resource/dist/vue-resource.js");
  }
}

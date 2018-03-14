package org.licket.semantic.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class SemanticUILibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

  public SemanticUILibraryResource() {
    super("vue-semantic.js", "js/vue-semantic.js");
  }
}

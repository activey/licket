package org.licket.semantic.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class SemanticLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public SemanticLibraryResource() {
        super("semantic.js", "js/semantic.min.js");
    }
}

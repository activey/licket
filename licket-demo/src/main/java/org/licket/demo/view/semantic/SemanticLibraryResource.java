package org.licket.demo.view.semantic;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptResource;

/**
 * @author activey
 */
public class SemanticLibraryResource extends JavascriptResource implements HeadParticipatingResource {

    public SemanticLibraryResource() {
        super("semantic.js", "js/semantic.min.js");
    }
}

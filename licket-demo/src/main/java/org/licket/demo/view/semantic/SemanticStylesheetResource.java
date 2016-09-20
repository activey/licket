package org.licket.demo.view.semantic;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.css.StylesheetResource;

/**
 * @author grabslu
 */
public class SemanticStylesheetResource extends StylesheetResource implements HeadParticipatingResource {

    public SemanticStylesheetResource() {
        super("semantic.css", "css/semantic.min.css");
    }
}

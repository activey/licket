package org.licket.semantic.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.MergedJavascriptStaticResource;

import static org.licket.core.resource.javascript.JavascriptStaticResource.javascriptResource;

/**
 * @author activey
 */
public class SemanticUILibraryResource extends MergedJavascriptStaticResource implements HeadParticipatingResource {

    public SemanticUILibraryResource() {
        super("ng-semantic.all.js",
                javascriptResource("modal.js", "ng-semantic/modal/modal.js"),
                javascriptResource("segment.js", "ng-semantic/segment/segment.js"),
                javascriptResource("ng-semantic", "ng-semantic/ng-semantic.js")

        );
    }
}

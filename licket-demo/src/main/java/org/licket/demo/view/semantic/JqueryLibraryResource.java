package org.licket.demo.view.semantic;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class JqueryLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public JqueryLibraryResource() {
        super("jquery.js", "js/jquery-3.1.0.js");
    }
}

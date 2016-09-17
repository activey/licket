package org.licket.core.resource.angular;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class AngularPolyfillsLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public AngularPolyfillsLibraryResource() {
        super("angular2-polyfills.js", "static/angular2-polyfills.js");
    }
}

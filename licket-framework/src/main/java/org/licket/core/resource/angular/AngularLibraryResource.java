package org.licket.core.resource.angular;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class AngularLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public AngularLibraryResource() {
        super("angular2-all.umd.dev.js", "static/angular2-all.umd.dev.js");
    }
}

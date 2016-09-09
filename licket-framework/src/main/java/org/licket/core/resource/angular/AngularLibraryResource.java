package org.licket.core.resource.angular;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptResource;

/**
 * @author activey
 */
public class AngularLibraryResource extends JavascriptResource implements HeadParticipatingResource {

    public AngularLibraryResource() {
        super("angular.js", "static/angular.min.js");
    }
}

package org.licket.core.resource.angular;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class RxLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public RxLibraryResource() {
        super("Rx.umd.js", "static/Rx.umd.js");
    }
}

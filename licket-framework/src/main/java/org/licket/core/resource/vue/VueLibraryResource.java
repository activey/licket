package org.licket.core.resource.vue;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.JavascriptStaticResource;

/**
 * @author activey
 */
public class VueLibraryResource extends JavascriptStaticResource implements HeadParticipatingResource {

    public VueLibraryResource() {
        super("vue.js", "vue/dist/vue.js");
    }
}

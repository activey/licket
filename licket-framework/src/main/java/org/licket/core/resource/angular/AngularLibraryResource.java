package org.licket.core.resource.angular;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.MergedJavascriptStaticResource;

import static org.licket.core.resource.javascript.JavascriptStaticResource.javascriptResource;

/**
 * @author activey
 */
public class AngularLibraryResource extends MergedJavascriptStaticResource implements HeadParticipatingResource {

    public AngularLibraryResource() {
        super("angular.all.js",
                javascriptResource("Rx.js", "rxjs/bundles/Rx.js"),
                javascriptResource("Reflect.js", "reflect-metadata/Reflect.js"),
                javascriptResource("zone.js", "zone.js/dist/zone.js"),
                javascriptResource("core.umd.js", "@angular/core/bundles/core.umd.js"),
                javascriptResource("common.umd.js", "@angular/common/bundles/common.umd.js"),
                javascriptResource("compiler.umd.js", "@angular/compiler/bundles/compiler.umd.js"),
                javascriptResource("platform-browser.umd.js", "@angular/platform-browser/bundles/platform-browser.umd.js"),
                javascriptResource("platform-browser-dynamic.umd.js", "@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js"),
                javascriptResource("router.umd.js", "@angular/router/bundles/router.umd.js"),
                javascriptResource("http.umd.js", "@angular/http/bundles/http.umd.js"),
                javascriptResource("forms.umd.js", "@angular/forms/bundles/forms.umd.js"),
                javascriptResource("upgrade.umd.js", "@angular/upgrade/bundles/upgrade.umd.js")

        );
    }
}

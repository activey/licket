package org.licket.core.view.hippo.testing.service;

import org.licket.core.view.hippo.testing.AngularClass;
import org.licket.core.view.hippo.testing.Dependency;
import org.licket.core.view.hippo.testing.annotation.AngularClassConstructor;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author grabslu
 */
public class HttpCommunication implements AngularClass {

    @Override
    public String getAngularClassName() {
        return "ng.http.Http";
    }
}

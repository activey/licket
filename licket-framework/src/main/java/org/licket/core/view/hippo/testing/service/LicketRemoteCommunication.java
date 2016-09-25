package org.licket.core.view.hippo.testing.service;

import org.licket.core.view.hippo.testing.AngularClass;
import org.licket.core.view.hippo.testing.annotation.AngularClassConstructor;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author grabslu
 */
public class LicketRemoteCommunication implements AngularClass {

    private HttpCommunication http;

    @Override
    public String getAngularClassName() {
        return "app.ComponentCommunicationService";
    }

    @AngularClassFunction
    public void invokeComponentAction(BlockBuilder body, NameBuilder actionData, NameBuilder responseListener,
                                      NameBuilder method) {}

    @AngularClassConstructor
    public void constructor(NameBuilder http) {}
}

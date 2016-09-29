package org.licket.core.module.http;

import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author grabslu
 */
public class HttpCommunicationService implements AngularInjectable {

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(NameBuilder.name("ng"), NameBuilder.name("http")), NameBuilder.name("Http"));
    }
}

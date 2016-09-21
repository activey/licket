package org.licket.core.view;

import org.licket.core.view.container.LicketComponentContainer;

import static java.lang.String.format;

/**
 * @author activey
 */
public class LicketUrls {

    public static final String CONTEXT_RESOURCES = "/licket/resource";

    public static final String CONTEXT_COMPONENT = "/licket/component";

    public static String componentContainerViewUrl(LicketComponentContainer<?> licketComponent) {
        return CONTEXT_COMPONENT + format("/%s/view", licketComponent.getCompositeId().getValue());
    }
}

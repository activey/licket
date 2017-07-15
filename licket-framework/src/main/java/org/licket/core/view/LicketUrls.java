package org.licket.core.view;

import static java.lang.String.format;

/**
 * @author activey
 */
public class LicketUrls {

    public static final String CONTEXT_RESOURCES = "/licket/resource";
    public static final String CONTEXT_FORM = "/licket/form";
    public static final String CONTEXT_ACTION_LINK = "/licket/link";
    public static final String CONTEXT_COMPONENT = "/licket/component";
    public static final String CONTEXT_DEBUG = "/licket/debug";

    public static String componentViewUrl(LicketComponent<?> licketComponent) {
        return CONTEXT_COMPONENT + format("/%s/view", licketComponent.getCompositeId().getValue());
    }
}

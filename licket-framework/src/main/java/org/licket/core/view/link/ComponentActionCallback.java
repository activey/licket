package org.licket.core.view.link;

import org.licket.core.view.LicketComponent;

import java.util.function.Consumer;

import static java.util.Arrays.stream;

/**
 * @author activey
 */
public class ComponentActionCallback extends ComponentFunctionCallback {

    private LicketComponent<?>[] components = {};

    // TODO currently reloading of AbstractLicketList is not supported ;/
    public final void reload(LicketComponent<?>... components) {
        this.components = components;
    }

    public final void forEachToBeReloaded(Consumer<LicketComponent<?>> componentConsumer) {
        stream(components).forEach(componentConsumer);
    }
}

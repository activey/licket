package org.licket.core.view.container;

import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;

import java.util.function.Predicate;

/**
 * @author grabslu
 */
public interface LicketComponentContainer<T> extends LicketComponent<T> {

    LicketComponent<?> findChild(CompositeId compositeId);

    void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerConsumer);
}

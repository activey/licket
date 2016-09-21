package org.licket.core.view.container;

import org.licket.core.id.CompositeId;
import org.licket.core.view.ComponentContainerView;
import org.licket.core.view.LicketComponent;

import java.util.function.Predicate;

/**
 * @author grabslu
 */
public interface LicketComponentContainer<T> extends LicketComponent<T> {

    static boolean hasExternalizedView(LicketComponentContainer<?> container) {
        return container.getComponentContainerView().isExternalized();
    }

    LicketComponent<?> findChild(CompositeId compositeId);

    ComponentContainerView getComponentContainerView();

    void traverseDown(Predicate<LicketComponent<?>> componentConsumer);

    void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerConsumer);
}

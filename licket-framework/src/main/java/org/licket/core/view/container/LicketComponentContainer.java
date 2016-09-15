package org.licket.core.view.container;

import org.licket.core.id.CompositeId;
import org.licket.core.view.ComponentContainerView;
import org.licket.core.view.LicketComponent;

/**
 * @author grabslu
 */
public interface LicketComponentContainer<T> extends LicketComponent<T> {

    LicketComponent<?> findChild(CompositeId compositeId);

    ComponentContainerView getComponentContainerView();
}

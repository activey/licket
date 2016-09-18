package org.licket.core.view;

import org.licket.core.view.container.LicketComponentContainer;

/**
 * @author activey
 */
public interface ComponentVisitor {

    void visitComponentContainer(LicketComponentContainer<?> container);

    void visitSimpleComponent(LicketComponent<?> component);
}

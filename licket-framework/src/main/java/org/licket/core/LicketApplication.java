package org.licket.core;

import org.licket.core.id.CompositeId;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.LicketComponentContainer;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author grabslu
 */
public interface LicketApplication {

    String getName();

    LicketComponentContainer<?> getRootComponentContainer();

    Optional<LicketComponent<?>> findComponent(CompositeId compositeId);

    Optional<LicketComponent<?>> findComponent(String compositeIdValue);

    void traverseDown(Predicate<LicketComponent<?>> componentVisitor);

    void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerVisitor);
}

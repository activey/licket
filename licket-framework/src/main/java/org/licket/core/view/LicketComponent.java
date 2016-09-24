package org.licket.core.view;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.of;

/**
 * @author activey
 */
public interface LicketComponent<T> {

    String getId();

    String getNormalizedId();

    CompositeId getCompositeId();

    LicketModel<T> getComponentModel();

    void setComponentModel(LicketModel<T> componentModel);

    void setComponentModelObject(T componentModelObject);

    void setParent(LicketComponent<?> parent);

    boolean hasParent();

    void initialize();

    void render(ComponentRenderingContext renderingContext);

    void invokeAction();

    Optional<LicketComponent<?>> traverseUp(Predicate<LicketComponent<?>> componentTraverser);
}

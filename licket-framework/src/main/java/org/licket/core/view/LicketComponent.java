package org.licket.core.view;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;

import static java.util.Optional.of;

/**
 * @author activey
 */
public interface LicketComponent<T> {
    LicketModel<T> getComponentModel();

    void setComponentModel(LicketModel<T> componentModel);

    void setComponentModelObject(T componentModelObject);

    void setParent(LicketComponent<?> parent);

    String getId();

    CompositeId getCompositeId();

    void initialize();

    void render(ComponentRenderingContext renderingContext);

    Optional<LicketComponent<?>> traverseUp(ComponentTraverser componentTraverser);

    boolean hasParent();
}

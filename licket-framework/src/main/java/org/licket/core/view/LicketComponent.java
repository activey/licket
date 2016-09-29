package org.licket.core.view;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularInjectable;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author activey
 */
public interface LicketComponent<T> extends AngularClass {

    static boolean hasExternalizedView(LicketComponentContainer<?> container) {
        return container.getView().isExternalized();
    }

    ComponentView getView();

    String getId();

    CompositeId getCompositeId();

    Class<T> getComponentModelClass();

    LicketModel<T> getComponentModel();

    void setComponentModel(LicketModel<T> componentModel);

    void setComponentModelObject(T componentModelObject);

    void setParent(LicketComponent<?> parent);

    void initialize();

    void render(ComponentRenderingContext renderingContext);

    Optional<LicketComponent<?>> traverseUp(Predicate<LicketComponent<?>> componentTraverser);
}

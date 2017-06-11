package org.licket.core.view;

import org.licket.core.LicketApplication;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author activey
 */
public interface LicketComponent<TYPE> extends VueClass {

    LicketComponentView getView();

    String getId();

    CompositeId getCompositeId();

    Class<TYPE> getComponentModelClass();

    LicketComponentModel<TYPE> getComponentModel();

    void setComponentModel(LicketComponentModel<TYPE> componentModel);

    void setComponentModelObject(TYPE componentModelObject);

    void setParent(LicketComponent<?> parent);

    void initialize();

    void render(ComponentRenderingContext renderingContext);

    Optional<LicketComponent<?>> traverseUp(Predicate<LicketComponent<?>> componentTraverser);

    // maybe it would make sense to have some components more complicated inner structure?
    default void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {}

    boolean isRoot(LicketApplication licketApplication);

    default boolean isCustom() {
        return false;
    }

    default boolean isStateful() {
        return true;
    }
}

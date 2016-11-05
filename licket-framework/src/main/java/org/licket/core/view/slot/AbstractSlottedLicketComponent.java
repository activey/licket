package org.licket.core.view.slot;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.function.Predicate;

/**
 * @author activey
 */
public class AbstractSlottedLicketComponent<T> extends AbstractLicketComponent<T> {

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass) {
        super(id, modelClass);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel) {
        super(id, modelClass, componentModel);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view) {
        super(id, modelClass, componentModel, view);
    }

    public final void slot(LicketComponent<?> component) {

    }

    public final void slot(String slotName, LicketComponent<?> component) {

    }

    @Override
    public final void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {

    }
}

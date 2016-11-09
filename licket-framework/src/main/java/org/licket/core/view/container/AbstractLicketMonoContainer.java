package org.licket.core.view.container;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.AbstractReloadableLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.LicketComponentView;
import java.util.function.Predicate;

/**
 * @author grabslu
 */
public abstract class AbstractLicketMonoContainer<T> extends AbstractReloadableLicketComponent<T> implements LicketComponentContainer<T> {

    private LicketComponent<?> child;

    public AbstractLicketMonoContainer(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass, modelReloader);
    }

    public AbstractLicketMonoContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                       LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, modelReloader);
    }

    public AbstractLicketMonoContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                       LicketComponentView view, LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, view, modelReloader);
    }

    public final void add(LicketComponent<?> child) {
        this.child = child;
        child.setParent(this);
    }

    @Override
    protected final void onInitialize() {
        onInitializeContainer();
        if (child == null) {
            return;
        }
        child.initialize();
    }

    protected void onInitializeContainer() {}

    @Override
    public void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {
        if (child == null) {
            return;
        }
        if (componentConsumer.test(child)) {
            child.traverseDown(componentConsumer);
        }
    }
}

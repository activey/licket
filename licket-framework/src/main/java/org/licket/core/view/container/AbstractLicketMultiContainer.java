package org.licket.core.view.container;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

import java.util.List;
import java.util.function.Predicate;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.AbstractReloadableLicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public abstract class AbstractLicketMultiContainer<T> extends AbstractReloadableLicketComponent<T> implements LicketComponentContainer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketMultiContainer.class);
    private List<LicketComponent<?>> leaves = newArrayList();

    public AbstractLicketMultiContainer(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass, modelReloader);
    }

    public AbstractLicketMultiContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                        LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, modelReloader);
    }

    public AbstractLicketMultiContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view,
                                        LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, view, modelReloader);
    }

    @Override
    protected final void onBeforeRender(ComponentRenderingContext renderingContext) {
        onRenderContainer(renderingContext);
    }

    protected void onRenderContainer(ComponentRenderingContext renderingContext) {}

    public final void add(LicketComponent<?> licketComponent) {
        if (leaves.contains(licketComponent)) {
            LOGGER.trace("Licket component [{}] already used as a leaf!", licketComponent.getId());
            return;
        }
        licketComponent.setParent(this);
        leaves.add(licketComponent);
    }

    @Override
    protected final void onInitialize() {
        onInitializeContainer();
        leaves.forEach(LicketComponent::initialize);
    }

    protected void onInitializeContainer() {}

    public final void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        leaves.forEach(leaf -> {
            if (componentVisitor.test(leaf)) {
                leaf.traverseDown(componentVisitor);
            }
        });
    }
}

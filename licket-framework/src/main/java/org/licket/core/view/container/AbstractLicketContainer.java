package org.licket.core.view.container;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.core.model.LicketModel.emptyModel;

import java.util.List;
import java.util.function.Predicate;
import javax.xml.stream.XMLStreamException;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.ComponentContainerView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public abstract class AbstractLicketContainer<T> extends AbstractLicketComponent<T> implements LicketComponentContainer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketContainer.class);
    private List<LicketComponentContainer<?>> branches = newArrayList();
    private List<LicketComponent<?>> leaves = newArrayList();
    private ComponentContainerView containerView;

    public AbstractLicketContainer(String id, Class<T> modelClass, ComponentContainerView componentView) {
        this(id, modelClass, componentView, emptyModel());
    }

    public AbstractLicketContainer(String id, Class<T> modelClass, ComponentContainerView containerView,
                                   LicketModel<T> componentModel) {
        super(id, modelClass, componentModel);
        this.containerView = containerView;
    }

    protected final void add(LicketComponent<?> licketComponent) {
        if (branches.contains(licketComponent)) {
            LOGGER.trace("Licket component [{}] already used as a branch!", licketComponent.getId());
            return;
        }
        licketComponent.setParent(this);
        leaves.add(licketComponent);
    }

    protected final void add(LicketComponentContainer<?> licketComponentContainer) {
        licketComponentContainer.setParent(this);
        branches.add(licketComponentContainer);
    }

    @Override
    protected final void onInitialize() {
        branches.forEach(LicketComponent::initialize);
        leaves.forEach(LicketComponent::initialize);
        onInitializeContainer();
    }

    protected void onInitializeContainer() {}

    @Override
    protected final void onRender(ComponentRenderingContext renderingContext) {
        doRenderContainer(renderingContext);
        onRenderContainer(renderingContext);
    }

    private void doRenderContainer(ComponentRenderingContext renderingContext) {
        if (!getComponentContainerView().isExternalized()) {
            LOGGER.trace("Using non-externalized view for component container: [{}]", getId());
            return;
        }
        renderingContext.onSurfaceElement(element -> {
            try {
                renderingContext.renderResource(
                    new ByteArrayResource(getCompositeId().getValue(), "text/html", element.toBytes()));

                element.replaceWith(new SurfaceElement(getId(), element.getNamespace()));
                element.detach();
            } catch (XMLStreamException e) {
                LOGGER.error("An error occured while rendering component container.", e);
                return;
            }
        });
    }

    protected void onRenderContainer(ComponentRenderingContext renderingContext) {}

    public final void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
        leaves.forEach(componentVisitor::test);
        branches.forEach(branch -> {
            if (componentVisitor.test(branch)) {
                branch.traverseDown(componentVisitor);
            }
        });
    }

    @Override
    public final void traverseDownContainers(Predicate<LicketComponentContainer<?>> containerVisitor) {
        branches.forEach(branch -> {
            if (containerVisitor.test(branch)) {
                branch.traverseDownContainers(containerVisitor);
            }
        });
    }

    @Override
    public final LicketComponent<?> findChild(CompositeId compositeId) {
        if (!compositeId.hasMore()) {
            if (compositeId.current().equals(getId())) {
                return this;
            }
            for (LicketComponent<?> leaf : leaves) {
                if (leaf.getId().equals(compositeId.current())) {
                    return leaf;
                }
            }
            for (LicketComponentContainer<?> branch : branches) {
                if (!branch.getId().equals(compositeId.current())) {
                    continue;
                }
                LicketComponent<?> childComponent = branch.findChild(compositeId);
                if (childComponent != null) {
                    return childComponent;
                }
            }
            return null;
        }

        compositeId.forward();

        for (LicketComponent<?> leaf : leaves) {
            if (leaf.getId().equals(compositeId.current())) {
                return leaf;
            }
        }
        for (LicketComponentContainer<?> branch : branches) {
            if (!branch.getId().equals(compositeId.current())) {
                continue;
            }
            LicketComponent<?> childComponent = branch.findChild(compositeId);
            if (childComponent != null) {
                return childComponent;
            }
        }
        return null;
    }

    @Override
    public final ComponentContainerView getComponentContainerView() {
        return containerView;
    }
}

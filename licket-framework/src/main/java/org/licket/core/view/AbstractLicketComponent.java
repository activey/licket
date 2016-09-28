package org.licket.core.view;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

    private String id;
    private Class<T> modelClass;
    private LicketModel<T> componentModel;
    private LicketComponent<?> parent;

    public AbstractLicketComponent(String id, Class<T> modelClass) {
        this(id, modelClass, emptyModel());
    }

    public AbstractLicketComponent(String id, Class<T> modelClass, LicketModel<T> componentModel) {
        this.id = id;
        this.modelClass = modelClass;
        this.componentModel = componentModel;
    }

    @PostConstruct
    public final void initialize() {
        LOGGER.debug("Initializing component: {}", id);
        onInitialize();
    }

    protected void onInitialize() {}

    public final void render(ComponentRenderingContext renderingContext) {
        LOGGER.debug("Rendering component: {}", id);
        onRender(renderingContext);
    }

    protected void onRender(ComponentRenderingContext renderingContext) {}

    @Override
    public final LicketModel<T> getComponentModel() {
        return componentModel;
    }

    @Override
    public final Class<T> getComponentModelClass() {
        return modelClass;
    }

    @Override
    public final void setComponentModel(LicketModel<T> componentModel) {
        this.componentModel = componentModel;
    }

    @Override
    public final void setComponentModelObject(T componentModelObject) {
        componentModel.set(componentModelObject);
    }

    @Override
    public final void setParent(LicketComponent<?> parent) {
        this.parent = parent;
    }

    @Override
    public final String getId() {
        return id;
    }

    public final CompositeId getCompositeId() {
        Optional<LicketComponent<?>> parentOptional = traverseUp(component -> true);
        if (!parentOptional.isPresent()) {
            return fromStringValue(id);
        }
        return fromStringValueWithAdditionalParts(parentOptional.get().getCompositeId().getValue(), id);
    }

    public final Optional<LicketComponent<?>> traverseUp(Predicate<LicketComponent<?>> componentTraverser) {
        if (parent == null) {
            return empty();
        }
        if (componentTraverser.test(parent)) {
            return of(parent);
        }
        return parent.traverseUp(componentTraverser);
    }

    @Override
    public PropertyNameBuilder angularName() {
        return property(name("app"), name(getCompositeId().getNormalizedValue()));
    }
}

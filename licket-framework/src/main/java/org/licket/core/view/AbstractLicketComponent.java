package org.licket.core.view;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.internal;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import javax.xml.stream.XMLStreamException;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

    private String id;
    private Class<T> modelClass;
    private LicketModel<T> componentModel;
    private ComponentView view;
    private LicketComponent<?> parent;
    private boolean initialized;

    public AbstractLicketComponent(String id, Class<T> modelClass) {
        this(id, modelClass, emptyModel(), internal());
    }

    public AbstractLicketComponent(String id, Class<T> modelClass, LicketModel<T> componentModel) {
        this(id, modelClass, componentModel, internal());
    }

    public AbstractLicketComponent(String id, Class<T> modelClass, LicketModel<T> componentModel, ComponentView view) {
        this.id = id;
        this.modelClass = modelClass;
        this.componentModel = componentModel;
        this.view = view;
    }

    @PostConstruct
    public final void initialize() {
        if (initialized) {
            return;
        }
        LOGGER.debug("Initializing component: {}", id);
        onInitialize();

        this.initialized = true;
    }

    protected void onInitialize() {}

    @Override
    public final LicketModel<T> getComponentModel() {
        return componentModel;
    }

    @Override
    public final void setComponentModel(LicketModel<T> componentModel) {
        this.componentModel = componentModel;
    }

    @Override
    public final Class<T> getComponentModelClass() {
        return modelClass;
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

    public final LicketComponent<?> getParent() {
        return parent;
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
    public PropertyNameBuilder vueName() {
        return property(name("app"), name(getCompositeId().getNormalizedValue()));
    }

    @Override
    public final ComponentView getView() {
        return view;
    }

    public final void render(ComponentRenderingContext renderingContext) {
        LOGGER.debug("Rendering component: {}", id);
        onRender(renderingContext);
        doRender(renderingContext);
    }

    private void doRender(ComponentRenderingContext renderingContext) {
        if (!getView().isExternalized()) {
            LOGGER.trace("Using non-externalized view for component: [{}]", getId());
            return;
        }
        renderingContext.onSurfaceElement(element -> {
            try {
                renderingContext
                        .renderResource(new ByteArrayResource(getCompositeId().getValue(), "text/html", element.toBytes()));

                element.replaceWith(new SurfaceElement(getId(), element.getNamespace()));
                element.detach();
            } catch (XMLStreamException e) {
                LOGGER.error("An error occured while rendering component.", e);
                return;
            }
        });
    }

    protected void onRender(ComponentRenderingContext renderingContext) {}

    @Override
    public void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {
        // do nothing by default
    }
}

package org.licket.core.view;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.view.api.AbstractLicketComponentAPI;
import org.licket.core.view.api.DefaultLicketComponentAPI;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.mount.params.MountingParams;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.xml.stream.XMLStreamException;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.module.application.LicketComponentModelReloader.callReloadComponent;
import static org.licket.core.view.LicketComponentView.noView;
import static org.licket.core.view.hippo.vue.annotation.VueComponentFunctionPredicate.MOUNTED_ONLY;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

    private String id;
    private Class<T> modelClass;
    private LicketComponentModel<T> componentModel;
    private LicketComponentView view;
    private LicketComponent<?> parent;
    private boolean initialized;
    private boolean custom;

    public AbstractLicketComponent(String id, Class<T> modelClass) {
        this(id, modelClass, emptyComponentModel(), noView());
    }

    public AbstractLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel) {
        this(id, modelClass, componentModel, noView());
    }

    public AbstractLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                   LicketComponentView view) {
        this.id = checkNotNull(id, "Component ID can not be null!");
        this.modelClass = checkNotNull(modelClass, "Model class can not be null!");
        this.componentModel = checkNotNull(componentModel,
            "Component model can not be null! Use emptyComponentModel() instead.");
        this.view = checkNotNull(view, "Component view can not be null!");
    }

    @PostConstruct
    public final void initialize() {
        if (initialized) {
            LOGGER.warn("Component is already initialized: {}", getCompositeId().getValue());
            return;
        }
        LOGGER.debug("Initializing component: {}", getCompositeId().getValue());
        onInitialize();
        this.initialized = true;
    }

    protected void onInitialize() {}

    @Override
    public final LicketComponentModel<T> getComponentModel() {
        return componentModel;
    }

    @Override
    public final void setComponentModel(LicketComponentModel<T> componentModel) {
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

    @Override
    public final void setParent(LicketComponent<?> parent) {
        this.parent = parent;
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
    public NameBuilder vueName() {
        return name(getCompositeId().getNormalizedValue());
    }

    @Override
    public final LicketComponentView getView() {
        return view;
    }

    public final void render(ComponentRenderingContext renderingContext) {
        LOGGER.debug("Rendering component: {}", getCompositeId().getValue());
        onBeforeRender(renderingContext);
        doRender(renderingContext);
    }

    protected void onBeforeRender(ComponentRenderingContext renderingContext) {}

    private void doRender(ComponentRenderingContext renderingContext) {
        if (!getView().hasTemplate()) {
            LOGGER.trace("No separate view for component component: [{}], using original element content.", getId());
            return;
        }
        renderingContext.onSurfaceElement(element -> {
            Optional<SurfaceElement> elementOptional = overrideComponentElement(element, renderingContext);
            if (elementOptional.isPresent()) {
                SurfaceElement replacedElement = elementOptional.get();
                generateComponentTemplate(renderingContext, element);
                element.replaceWith(replacedElement);
                element.detach();
                return;
            }

            generateComponentTemplate(renderingContext, element);
            generateComponentElement(element);
        });
    }

    private void generateComponentTemplate(ComponentRenderingContext renderingContext, SurfaceElement element) {
        try {
            if (!getView().isTemplateExternal()) {
                renderingContext
                        .renderResource(new ByteArrayResource(getCompositeId().getValue(), "text/html", element.toBytes()));
            }
        } catch (XMLStreamException e) {
            LOGGER.error("An error occurred while rendering component.", e);
        }
    }

    protected Optional<SurfaceElement> overrideComponentElement(SurfaceElement surfaceElement, ComponentRenderingContext renderingContext) {
        // when overriding it to override component rendering and providing
        return Optional.empty();
    }

    private void generateComponentElement(SurfaceElement element) {
        SurfaceElement componentElement = new SurfaceElement(getId(), element.getNamespace());
        setRefAttribute(componentElement);
        element.replaceWith(componentElement);
        element.detach();
    }

    private void setRefAttribute(SurfaceElement element) {
        element.addAttribute("ref", getId());
    }

    public AbstractLicketComponentAPI api(ComponentFunctionCallback componentFunctionCallback) {
        return new DefaultLicketComponentAPI(this, componentFunctionCallback);
    }

    @Override
    public final boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    @SuppressWarnings("unused")
    public final void mountComponent(MountingParams mountingParams, ComponentActionCallback componentActionCallback) {
        onComponentMounted(mountingParams);
        onAfterComponentMounted(componentActionCallback);
    }

    @VueComponentFunction(predicates = MOUNTED_ONLY)
    public final void afterMount(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        // setting current form model directly without event emitter
        functionBody
                .appendStatement(
                        expressionStatement(assignment().left(property(thisLiteral(), name("model")))
                                .right(arrayElementGet()
                                        .target(
                                                property(property("response", "body"), "model"))
                                        .element(stringLiteral(getCompositeId().getValue())))));
        // gathering all others
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterComponentMounted(componentActionCallback);

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded((component, patch) -> functionBody.appendStatement(expressionStatement(callReloadComponent(component, patch))));

        // invoking javascript calls
        componentActionCallback.forEachCall(call -> functionBody.appendStatement(
                expressionStatement(call)
        ));
    }

    /**
     * Override to be able to update component model upon mounting params entity
     * @param componentMountingParams
     */
    protected void onComponentMounted(MountingParams componentMountingParams) {}

    /**
     *
     * @param componentActionCallback
     */
    protected void onAfterComponentMounted(ComponentActionCallback componentActionCallback) {}
}

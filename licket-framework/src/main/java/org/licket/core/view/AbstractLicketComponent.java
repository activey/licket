package org.licket.core.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.noView;
import static org.licket.framework.hippo.NameBuilder.name;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import javax.xml.stream.XMLStreamException;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.resource.ProxyResource;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.NameBuilder;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

  private String id;
  private Class<T> modelClass;
  private LicketComponentModel<T> componentModel;
  private LicketComponentView view;
  private LicketComponent<?> parent;
  private boolean initialized;

  public AbstractLicketComponent(String id, Class<T> modelClass) {
    this(id, modelClass, emptyComponentModel(), noView());
  }

  public AbstractLicketComponent(String id, Class<T> modelClass,
      LicketComponentModel<T> componentModel) {
    this(id, modelClass, componentModel, noView());
  }

  public AbstractLicketComponent(String id, Class<T> modelClass,
      LicketComponentModel<T> componentModel, LicketComponentView view) {
    this.id = checkNotNull(id, "Component ID can not be null!");
    this.modelClass = checkNotNull(modelClass, "Model class can not be null!");
    this.componentModel = checkNotNull(componentModel,
        "Component model can not be null! Use emptyComponentModel() instead.");
    this.view = checkNotNull(view, "Component view can not be null!");
  }

  @PostConstruct
  public final void initialize() {
    if (initialized) {
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

  public final Optional<LicketComponent<?>> traverseUp(
      Predicate<LicketComponent<?>> componentTraverser) {
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
      LOGGER.trace(
          "No separate view for component component: [{}], using original element content.",
          getId());
      return;
    }
    renderingContext.onSurfaceElement(element -> {
      setTemplate(renderingContext, element);
    });
  }

  private void setTemplate(ComponentRenderingContext renderingContext, SurfaceElement element) {
    try {
      if (getView().isTemplateExternal()) {
//        renderingContext.renderResource(
//            new ProxyResource(getView().viewResource(), getCompositeId().getValue()));
      } else {
        renderingContext.renderResource(
            new ByteArrayResource(getCompositeId().getValue(), "text/html", element.toBytes()));
      }
      onElementReplaced(replaceElement(element));
    } catch (XMLStreamException e) {
      LOGGER.error("An error occured while rendering component.", e);
      return;
    }
  }

  protected void onElementReplaced(SurfaceElement surfaceElement) {}

  private SurfaceElement replaceElement(SurfaceElement element) {
    SurfaceElement componentElement = new SurfaceElement(getId(), element.getNamespace());
    setRefAttribute(componentElement);
    element.replaceWith(componentElement);
    element.detach();

    return componentElement;
  }

  private void setRefAttribute(SurfaceElement element) {
    element.addAttribute("ref", getId());
  }
}

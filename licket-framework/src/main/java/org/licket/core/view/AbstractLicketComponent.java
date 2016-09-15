package org.licket.core.view;

import static java.util.Optional.of;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.id.CompositeId.fromStringValueWithAdditionalParts;
import static org.licket.core.model.LicketModel.empty;
import static org.licket.surface.element.ElementTraverser.withComponentIdSet;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.element.ElementTraverser;
import org.licket.surface.element.SurfaceElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.awt.Component;
import java.util.Optional;

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

  private String id;
  private LicketModel<T> componentModel;
  private LicketComponent<?> parent;

  public AbstractLicketComponent(String id) {
    this(id, empty());
  }

  public AbstractLicketComponent(String id, LicketModel<T> componentModel) {
    this.id = id;
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

  public final Optional<LicketComponent<?>> traverseUp(ComponentTraverser componentTraverser) {
    if (parent == null) {
      return Optional.empty();
    }
    if (componentTraverser.componentMatch(parent)) {
      return of(parent);
    }
    return parent.traverseUp(componentTraverser);
  }

  @Override
  public final boolean hasParent() {
    return parent != null;
  }
}

package org.licket.core.view.container;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractReloadableLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.render.ComponentRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public abstract class AbstractLicketMultiContainer<T> extends AbstractReloadableLicketComponent<T> implements LicketComponentContainer<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketMultiContainer.class);
  private List<LicketComponent<?>> items = newArrayList();

  public AbstractLicketMultiContainer(String id, Class<T> modelClass) {
    super(id, modelClass);
  }

  public AbstractLicketMultiContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel) {
    super(id, modelClass, componentModel);
  }

  public AbstractLicketMultiContainer(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                      LicketComponentView view) {
    super(id, modelClass, componentModel, view);
  }

  @Override
  protected final void onBeforeRender(ComponentRenderingContext renderingContext) {
    onBeforeRenderContainer(renderingContext);
  }

  protected void onBeforeRenderContainer(ComponentRenderingContext renderingContext) {
  }

  public final void add(LicketComponent<?> licketComponent) {
    if (items.contains(licketComponent)) {
      LOGGER.trace("Licket component [{}] already used as a leaf!", licketComponent.getCompositeId().getValue());
      return;
    }
    licketComponent.setParent(this);
    items.add(licketComponent);
  }

  @Override
  protected final void onInitialize() {
    onInitializeContainer();
    items.forEach(LicketComponent::initialize);
  }

  protected void onInitializeContainer() {
  }

  public final void traverseDown(Predicate<LicketComponent<?>> componentVisitor) {
    items.forEach(item -> {
      if (componentVisitor.test(item)) {
        item.traverseDown(componentVisitor);
      }
    });
  }
}

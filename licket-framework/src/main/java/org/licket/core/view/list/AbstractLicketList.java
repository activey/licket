package org.licket.core.view.list;

import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.element.SurfaceElement;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

public abstract class AbstractLicketList extends AbstractLicketMultiContainer<ComponentModelProperty> {

  public AbstractLicketList(String id, ComponentModelProperty componentModelProperty) {
    super(id, ComponentModelProperty.class, ofModelObject(componentModelProperty), internalTemplateView());
    // TODO analyze element class provided and check its properties against passed enclosingComponentPropertyModel
  }

  @Override
  protected final Optional<SurfaceElement> overrideComponentElement(SurfaceElement surfaceElement, ComponentRenderingContext renderingContext) {
    SurfaceElement element = new SurfaceElement(getCompositeId().getNormalizedValue(), surfaceElement.getNamespace());
    setRefAttribute(element);
    setForAttribute(element);
    setBindAttribute(element);
    postProcess(element);
    return Optional.of(element);
  }

  protected void postProcess(SurfaceElement element) {

  }

  private void setRefAttribute(SurfaceElement element) {
    element.addAttribute("ref", getId());
  }

  private void setForAttribute(SurfaceElement element) {
    // TODO check if enclosing property model has collection defined with name from getComponentModel().get()
    element.addAttribute("v-for", format("%s in %s", getId(), getComponentModel().get().builder().build().toSource()));

    Optional<String> keyPropertyName = keyPropertyName();
    if (!keyPropertyName.isPresent()) {
      return;
    }
    element.addAttribute("v-bind:key", format("%s.%s", getId(), keyPropertyName.get()));
  }

  protected Optional<String> keyPropertyName() {
    return empty();
  }

  private void setBindAttribute(SurfaceElement element) {
    element.addAttribute("v-bind:model", getId());
  }

  @Override
  public boolean isStateful() {
    // list are stateless, so we can't put them into components tree, they have to be global defined
    return false;
  }
}

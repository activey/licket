package org.licket.core.view.media;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.render.ComponentRenderingContext;

import static org.licket.core.view.hippo.ComponentModelProperty.fromComponentModelProperty;

public class LicketImage extends AbstractLicketComponent<ComponentModelProperty> {
  public LicketImage(String id) {
    this(id, fromComponentModelProperty(id));
  }

  public LicketImage(String id, ComponentModelProperty componentModelProperty) {
    super(id, ComponentModelProperty.class, LicketComponentModel.ofModelObject(componentModelProperty));
  }

  @Override
  protected void onBeforeRender(ComponentRenderingContext renderingContext) {
    renderingContext.onSurfaceElement(element -> element.addAttribute("v-bind:src", getComponentModel().get().builder().build().toSource()));
  }
}

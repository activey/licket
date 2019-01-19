package org.licket.semantic.component.dimmer;

import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.render.ComponentRenderingContext;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author lukaszgrabski
 */
public class SemanticUIDimmerContainer extends AbstractLicketMultiContainer<DimmerSettings> {

  public SemanticUIDimmerContainer(String id, DimmerSettings dimmerSettings) {
    super(id, DimmerSettings.class, ofModelObject(dimmerSettings), internalTemplateView());
  }

  @Override
  protected void onRenderContainer(ComponentRenderingContext renderingContext) {
    boolean pageWide = getComponentModel().get().orElse(new DimmerSettings()).isPageWide();
    renderingContext.onSurfaceElement(surfaceElement -> {
      if (pageWide) {
        surfaceElement.addAttribute("class", "ui page dimmer");
      } else {
        surfaceElement.addAttribute("class", "ui dimmer");
      }
    });
  }
}

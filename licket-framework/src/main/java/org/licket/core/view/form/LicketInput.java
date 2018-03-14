package org.licket.core.view.form;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.render.ComponentRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.hippo.ComponentModelProperty.fromComponentModelProperty;

/**
 * @author activey
 */
public class LicketInput extends AbstractLicketComponent<ComponentModelProperty> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LicketInput.class);

  public LicketInput(String id, ComponentModelProperty componentModelProperty) {
    super(id, ComponentModelProperty.class, ofModelObject(componentModelProperty));
  }

  public LicketInput(String id) {
    this(id, fromComponentModelProperty(id));
  }

  @Override
  protected void onBeforeRender(ComponentRenderingContext renderingContext) {
    LOGGER.trace("Rendering LicketInput: [{}]", getId());

    Optional<LicketComponent<?>> parent = traverseUp(
            component -> component instanceof AbstractLicketMultiContainer);
    if (!parent.isPresent()) {
      return;
    }
    renderingContext.onSurfaceElement(element -> element.addAttribute("v-model", getComponentModel().get().builder().build().toSource()));
  }
}

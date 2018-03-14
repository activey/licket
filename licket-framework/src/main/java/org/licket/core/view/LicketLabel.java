package org.licket.core.view;

import org.licket.core.view.hippo.ComponentModelProperty;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static org.licket.core.model.LicketComponentModel.ofModelObject;

public class LicketLabel extends AbstractLicketComponent<ComponentModelProperty> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LicketLabel.class);

  public LicketLabel(String id) {
    this(id, ComponentModelProperty.fromComponentModelProperty(id));
  }

  public LicketLabel(String id, ComponentModelProperty modelLabelProperty) {
    super(id, ComponentModelProperty.class, ofModelObject(modelLabelProperty));
  }

  @Override
  protected final void onBeforeRender(ComponentRenderingContext renderingContext) {
    LOGGER.trace("Rendering LicketLabel: [{}]", getId());
    onBeforeLabelRender(renderingContext);
    renderingContext.onSurfaceElement(element -> {
      // clearing out whole label content
      element.removeChildren();
      // setting up label value template
      element.appendChildElement(new Text(format("{{%s}}", getComponentModel().get().builder().build().toSource())));
    });
  }

  protected void onBeforeLabelRender(ComponentRenderingContext renderingContext) {
  }
}

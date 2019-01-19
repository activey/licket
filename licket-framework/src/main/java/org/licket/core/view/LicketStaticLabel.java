package org.licket.core.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicketStaticLabel extends AbstractLicketComponent<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketStaticLabel.class);

    public LicketStaticLabel(String id, LicketComponentModel<String> labelModel) {
        super(id, String.class, labelModel);
    }

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        LOGGER.trace("Rendering LicketStaticLabel: [{}]", getId());
        getComponentModel().get().ifPresent(componentModel -> renderingContext.onSurfaceElement(element -> {
            // clearing out whole label content
            element.removeChildren();
            // setting up label value template
            element.appendChildElement(new Text(componentModel));
        }));
    }
}

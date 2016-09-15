package org.licket.core.view;

import org.licket.core.model.LicketModel;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class LicketLabel extends AbstractLicketComponent<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketLabel.class);

    public LicketLabel(String id) {
        super(id);
    }

    public LicketLabel(String id, LicketModel<String> labelModel) {
        super(id, labelModel);
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        LOGGER.trace("Rendering LicketLabel: [{}]", getId());
        renderingContext.onSurfaceElement(element -> {
            // clearing out whole label content
            element.removeChildren();
            // setting up label value template
            element.appendChildElement(new Text(labelTemplate()));
        });
    }

    private String labelTemplate() {
        return format("{{%s}}", getId());
    }
}

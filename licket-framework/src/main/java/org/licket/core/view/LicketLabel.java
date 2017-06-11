package org.licket.core.view;

import static java.lang.String.format;

import org.licket.core.model.ComponentIdModel;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LicketLabel extends AbstractLicketComponent<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketLabel.class);

    public LicketLabel(String id) {
        this(id, new ComponentIdModel(id));
    }

    /**
     *
     * @param id Label component id, unique on given tree level (or at least should be ;P)
     * @param labelModel Label placeholder model. Keep that in very mind: this model, typed on String, is intended to be
     * used as a placeholder generator for hippo one-way binding as placeholder is generated upon this schema:
     *
     * <code>
     *  {{%s}}.format(componentModel.get())
     * </code>
     *
     * Thats it no other logic behind.
     */
    public LicketLabel(String id, LicketComponentModel<String> labelModel) {
        super(id, String.class, labelModel);
    }

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        LOGGER.trace("Rendering LicketLabel: [{}]", getId());
        renderingContext.onSurfaceElement(element -> {
            // clearing out whole label content
            element.removeChildren();
            // setting up label value template
            element.appendChildElement(new Text(placeholder()));
        });
    }

    private String placeholder() {
        Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketMultiContainer);
        if (parent != null) {
            return format("{{model.%s}}", getComponentModel().get());
        }
        return format("{{%s}}", getComponentModel().get());
    }
}

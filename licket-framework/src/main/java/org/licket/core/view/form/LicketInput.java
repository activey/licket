package org.licket.core.view.form;

import static java.lang.String.format;
import java.util.Optional;
import org.licket.core.model.ComponentIdModel;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.render.ComponentRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class LicketInput extends AbstractLicketComponent<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketInput.class);

    public LicketInput(String id, LicketComponentModel<String> enclosingComponentPropertyModel) {
        super(id, String.class, enclosingComponentPropertyModel);
    }

    public LicketInput(String id) {
        this(id, new ComponentIdModel(id));
    }

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        LOGGER.trace("Rendering LicketInput: [{}]", getId());

        Optional<LicketComponent<?>> parent = traverseUp(
            component -> component instanceof AbstractLicketMultiContainer);
        if (!parent.isPresent()) {
            return;
        }
        AbstractLicketMultiContainer parentContainer = (AbstractLicketMultiContainer) parent.get();
        renderingContext.onSurfaceElement(element -> {
            // TODO refactor
            String firstPart = "model";
            if (!parentContainer.getView().hasTemplate()) {
                firstPart = parentContainer.getId();
            }
            element.addAttribute("v-model", format("%s.%s", firstPart, getComponentModel().get()));
            element.addAttribute("name", getComponentModel().get());
        });
    }
}

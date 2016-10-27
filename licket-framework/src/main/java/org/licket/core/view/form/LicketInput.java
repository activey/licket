package org.licket.core.view.form;

import static java.lang.String.format;
import java.util.Optional;
import org.licket.core.model.ComponentIdModel;
import org.licket.core.model.LicketModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.render.ComponentRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class LicketInput extends AbstractLicketComponent<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicketInput.class);

    public LicketInput(String id, LicketModel<String> enclosingComponentPropertyModel) {
        super(id, String.class, enclosingComponentPropertyModel);
    }

    public LicketInput(String id) {
        this(id, new ComponentIdModel(id));
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        LOGGER.trace("Rendering LicketInput: [{}]", getId());

        Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketContainer);
        if (!parent.isPresent()) {
            return;
        }
        AbstractLicketContainer parentContainer = (AbstractLicketContainer) parent.get();
        renderingContext.onSurfaceElement(element -> {
            // TODO refactor
            String firstPart = "model";
            if (!parentContainer.getView().isExternalized()) {
                firstPart = parentContainer.getId();
            }
            element.addAttribute("v-model", format("%s.%s", firstPart, getComponentModel().get()));
            element.setAttribute("name", getComponentModel().get());
        });
    }
}

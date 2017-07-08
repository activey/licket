package org.licket.core.view.media;

import org.licket.core.model.ComponentIdModel;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;

import static java.lang.String.format;

public class LicketImage extends AbstractLicketComponent<String> {

    public LicketImage(String id) {
        this(id, new ComponentIdModel(id));
    }

    public LicketImage(String id, LicketComponentModel<String> imageModel) {
        super(id, String.class, imageModel);
    }

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> element.addAttribute("v-bind:src", placeholder()));
    }

    private String placeholder() {
        Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketMultiContainer);
        if (parent != null) {
            return format("model.%s", getComponentModel().get());
        }
        return format("%s", getComponentModel().get());
    }
}

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
        placeholder().ifPresent(placeholder -> renderingContext.onSurfaceElement(element -> element.addAttribute("v-bind:src", placeholder)));
    }

    private Optional<String> placeholder() {
        return getComponentModel().get().map(modelValue -> {
            Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketMultiContainer);
            return parent
                .map(parentModel -> format("model.%s", modelValue))
                .orElse(format("%s", modelValue));
        });
    }
}

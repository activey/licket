package org.licket.core.view.list;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

import java.util.Optional;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.element.SurfaceElement;

public abstract class AbstractLicketList extends AbstractLicketMultiContainer<String> {

    public AbstractLicketList(String id, LicketComponentModel<String> enclosingComponentPropertyModel) {
        super(id, String.class, enclosingComponentPropertyModel, internalTemplateView());
        // TODO analyze element class provided and check its properties against passed enclosingComponentPropertyModel
    }

    @Override
    protected Optional<SurfaceElement> overrideComponentElement(SurfaceElement surfaceElement, ComponentRenderingContext renderingContext) {
        SurfaceElement element = new SurfaceElement(getCompositeId().getNormalizedValue(), surfaceElement.getNamespace());
        setRefAttribute(element);
        setForAttribute(element);
        setBindAttribute(element);
        return Optional.of(element);
    }

    private void setRefAttribute(SurfaceElement element) {
        element.addAttribute("ref", getId());
    }

    private void setForAttribute(SurfaceElement element) {
        // TODO check if enclosing property model has collection defined with name from getComponentModel().get()

        getComponentModel().get().ifPresent(componentModel -> {
            element.addAttribute("v-for", format("%s in model.%s", getId(), componentModel));
            Optional<String> keyPropertyName = keyPropertyName();
            if (!keyPropertyName.isPresent()) {
                return;
            }
            element.addAttribute("v-bind:key", format("%s.%s", getId(), keyPropertyName.get()));
        });

    }

    protected Optional<String> keyPropertyName() {
        return empty();
    }

    private void setBindAttribute(SurfaceElement element) {
        element.addAttribute("v-bind:model", getId());
    }

    @Override
    public boolean isStateful() {
        // list are stateless, so we can't put them into components tree, they have to be global defined
        return false;
    }
}

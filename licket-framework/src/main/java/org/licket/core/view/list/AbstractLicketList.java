package org.licket.core.view.list;

import static java.lang.String.format;
import static org.licket.core.view.ComponentView.internal;
import java.util.Optional;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.render.ComponentRenderingContext;

public abstract class AbstractLicketList<T> extends AbstractLicketContainer<String> {

    private Class<T> elementClass;

    public AbstractLicketList(String id, LicketModel<String> enclosingComponentPropertyModel, Class<T> elementClass,
                              LicketComponentModelReloader modelReloader) {
        super(id, String.class, enclosingComponentPropertyModel, internal(), modelReloader);
        this.elementClass = elementClass;
        // TODO analyze element class provided and check its properties against passed enclosingComponentPropertyModel
    }

    @Override
    protected final void onRenderContainer(ComponentRenderingContext renderingContext) {
        Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketContainer);
        if (!parent.isPresent()) {
            return;
        }
        AbstractLicketContainer parentContainer = (AbstractLicketContainer) parent.get();
        renderingContext.onSurfaceElement(element -> {
            String firstPart = "model";
            if (!parentContainer.getView().isExternalized()) {
                firstPart = parentContainer.getId();
            }
            element.addAttribute("v-for", format("%s in %s.%s", getId(), firstPart, getComponentModel().get()));
        });
    }
}

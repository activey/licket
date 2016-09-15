package org.licket.core.view.list;

import org.licket.core.model.LicketModel;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.attribute.BaseAttribute;

import static org.licket.core.model.LicketModel.empty;
import static org.licket.core.view.ComponentContainerView.internal;

public abstract class AbstractLicketList<T> extends AbstractLicketContainer<Iterable<T>> {

    public AbstractLicketList(String id, LicketModel<Iterable<T>> listModel) {
        super(id, internal(), listModel);
    }

    public AbstractLicketList(String id) {
        this(id, empty());
    }

    @Override
    protected final void onRenderContainer(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> element.addAttribute("*ngFor", "let value of values"));
    }
}

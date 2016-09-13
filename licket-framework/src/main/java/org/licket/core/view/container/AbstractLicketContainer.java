package org.licket.core.view.container;

import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.licket.core.resource.ByteArrayResource;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.surface.element.SurfaceElement;

/**
 * @author activey
 */
public abstract class AbstractLicketContainer<T> extends AbstractLicketComponent<T> {

    public AbstractLicketContainer(String id) {
        super(id);
    }

    public AbstractLicketContainer(String id, LicketComponentView componentView) {
        super(id, componentView);
    }

    public AbstractLicketContainer(String id, LicketComponentView componentView, LicketModel<T> componentModel) {
        super(id, componentView, componentModel);
    }

    @Override
    protected final void onRender(ComponentRenderingContext renderingContext) {
        // extracting surface DOM element as separate static resource


        // extracting element content as separate licket resource - angular component view
        CompositeId rootRelative = fromStringValueWithAdditionalParts(licketApplication.getRootComponent().getId(),
                getComponentCompositeId().getIdParts());

        // creating new static resource
        resourcesStorage.putResource(new ByteArrayResource(rootRelative.getValue(), "text/html", toXML().getBytes()));

        // setting tag name
        replaceWith(new SurfaceElement(getComponentId(), getBaseURI()));
        detach();

        super.onRender(renderingContext);
    }
}

package org.licket.core.view.link;

import static java.lang.String.format;
import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink extends AbstractLicketComponent<Void> {

    public AbstractLicketActionLink(String id) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(AbstractLicketActionLink.class));
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext.onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)",
            format("invokeAction('%s')", getCompositeId().getValue())));
    }

    protected final void onInvokeAction(Void voidModel) {
        onInvokeAction();
    }

    protected abstract void onInvokeAction();
}

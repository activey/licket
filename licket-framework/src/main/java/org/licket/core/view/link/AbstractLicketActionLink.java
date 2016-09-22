package org.licket.core.view.link;

import static java.lang.String.format;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink extends AbstractLicketComponent {

    public AbstractLicketActionLink(String id) {
        super(id);
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext.onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)",
            format("invokeAction('%s')", getCompositeId().getValue())));
    }

    protected abstract void onInvokeAction();
}

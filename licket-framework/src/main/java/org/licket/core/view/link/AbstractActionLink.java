package org.licket.core.view.link;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

/**
 * @author activey
 */
public abstract class AbstractActionLink extends AbstractLicketComponent {

    public AbstractActionLink(String id) {
        super(id);
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
    }

    protected abstract void onInvokeAction();
}

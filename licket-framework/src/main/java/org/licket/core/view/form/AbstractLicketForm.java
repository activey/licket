package org.licket.core.view.form;

import org.licket.core.model.LicketModel;
import org.licket.core.view.ComponentView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.AngularComponent;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;

/**
 * @author activey
 */
@AngularComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketContainer<T> {

    public AbstractLicketForm(String id, Class<T> modelClass, LicketModel<T> model, ComponentView componentView) {
        super(id, modelClass, model, componentView);
    }

    protected void onSubmit(T formModelObject) {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            element.setAttribute("(ngSubmit)", "submitForm()");
        });
    }

    @AngularClassFunction
    public void submitForm(BlockBuilder functionBlock) {
        // TODO here use functionBlock to define function body that will call http service
    }
}

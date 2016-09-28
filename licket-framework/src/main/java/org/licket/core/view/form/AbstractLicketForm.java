package org.licket.core.view.form;

import org.licket.core.model.LicketModel;
import org.licket.core.view.ComponentContainerView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.testing.annotation.AngularClassConstructor;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.AngularComponent;
import org.licket.core.view.hippo.testing.service.LicketRemoteCommunication;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;

/**
 * @author activey
 */
@AngularComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketContainer<T> {

    public AbstractLicketForm(String id, Class<T> modelClass, ComponentContainerView componentView,
                              LicketModel<T> model) {
        super(id, modelClass, componentView, model);
    }

    protected void onSubmit(T formModelObject) {}

    @Override
    protected void onRenderContainer(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            element.setAttribute("(ngSubmit)", "submitForm()");
        });
    }

    @AngularClassFunction
    private void submitForm(BlockBuilder functionBlock) {
        // TODO here use functionBlock to define function body that will call http service
    }

    @AngularClassConstructor
    public void constructor(LicketRemoteCommunication licketRemote) {

    }
}

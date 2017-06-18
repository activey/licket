package org.licket.core.view.link;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;

/**
 * @author activey
 */
public abstract class AbstractLicketLink extends AbstractLicketComponent<Void> {

    public AbstractLicketLink(String id) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView());
    }

    @VueComponentFunction
    public final void handleClick(BlockBuilder functionBlock) {
        ComponentActionCallback callback = new ComponentActionCallback();
        onClick(callback);

        callback.forEachCall(call -> functionBlock.appendStatement(
                expressionStatement(call)
        ));
    }

    protected void onClick(ComponentActionCallback callback) {}

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        renderingContext
                .onSurfaceElement(surfaceElement -> surfaceElement.addAttribute("v-on:click", "handleClick"));
    }
}

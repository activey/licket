package org.licket.core.view.link;

import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.module.application.LicketComponentModelReloader.callReloadComponent;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink<T> extends AbstractLicketComponent<T> {

    public AbstractLicketActionLink(String id, Class<T> modelClass) {
        super(id, modelClass, emptyComponentModel(), internalTemplateView());
    }

    @VueComponentFunction
    public final void afterClick(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterClick(componentActionCallback);

        // creating all after-click statements
        componentActionCallback.forEachCall(call -> functionBody.appendStatement(
                expressionStatement(call)
        ));

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded((component, patch) -> functionBody.appendStatement(expressionStatement(callReloadComponent(component, patch))));
    }

    @VueComponentFunction
    public final void handleClick(BlockBuilder functionBlock) {
        ComponentFunctionCallback callback = new ComponentFunctionCallback();
        onBeforeClick(callback);
        callback.forEachCall(call -> functionBlock.appendStatement(
                expressionStatement(call)
        ));

        FunctionCallBuilder functionCall = functionCall()
                .target(property(property(thisLiteral(), LicketRemote.serviceName()), name("handleActionLinkClick")))
                .argument(stringLiteral(getCompositeId().getValue()));
        decorateActionLinkModel(functionCall);
        functionCall.argument(property(thisLiteral(), name("afterClick")));
        functionBlock.appendStatement(expressionStatement(functionCall));
    }

    private void decorateActionLinkModel(FunctionCallBuilder functionCallBuilder) {
        if (getComponentModelClass().isAssignableFrom(Void.class)) {
            functionCallBuilder.argument(objectLiteral());
            return;
        }
        /*
          TODO if link model class is different than Void we just read parent component model data and send it over,
          maybe it should be more sophisticated
          */
        functionCallBuilder.argument(property(property("this", "$parent"), "model"));
    }

    @Override
    protected final void onBeforeRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.addAttribute("v-on:click", "handleClick"));
    }

    @SuppressWarnings("unused")
    public final void invokeAction(T modelObject, ComponentActionCallback componentActionCallback) {
        onClick(modelObject);
        onAfterClick(componentActionCallback);
    }

    protected void onClick(T modelObject) {}

    protected void onAfterClick(ComponentActionCallback componentActionCallback) {}

    protected void onBeforeClick(ComponentFunctionCallback componentFunctionCallback) {}
}

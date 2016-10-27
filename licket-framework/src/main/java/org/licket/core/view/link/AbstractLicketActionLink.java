package org.licket.core.view.link;

import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink extends AbstractLicketComponent<Void> {

    public AbstractLicketActionLink(String id, LicketRemote communicationService, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(AbstractLicketActionLink.class));
    }

    @VueComponentFunction
    public void afterClick(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterClick(componentActionCallback);

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded(component ->  {
            functionBody.appendStatement(
                    functionCall()
                            .target(property(name("modelReloader"), name("notifyModelChanged")))
                            .argument(stringLiteral(component.getCompositeId().getValue()))
                            .argument(arrayElementGet()
                                    .target(property(functionCall().target(property(name("response"), name("json"))), name("model")))
                                    .element(stringLiteral(component.getCompositeId().getValue())))
            );
        });
    }

    @VueComponentFunction
    public void handleClick(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("$licketRemote"), name("handleActionLinkClick")))
                                .argument(stringLiteral(getCompositeId().getValue()))
                                .argument(property(thisLiteral(), name("afterClick")))
                )
        );
    }

    protected void onAfterClick(ComponentActionCallback componentActionCallback) {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("v-on:click", "handleClick"));
    }

    public final void invokeAction(ComponentActionCallback componentActionCallback) {
        onInvokeAction();
        onAfterClick(componentActionCallback);
    }

    protected abstract void onInvokeAction();
}

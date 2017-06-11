package org.licket.core.view.link;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
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

    private LicketRemote licketRemote;
    private LicketComponentModelReloader modelReloader;

    public AbstractLicketActionLink(String id, LicketRemote licketRemote, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView());
        this.licketRemote = checkNotNull(licketRemote, "Licket remote must not be null!");
        this.modelReloader = checkNotNull(modelReloader, "Licket model reloader must not be null!");
    }

    @VueComponentFunction
    public void afterClick(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterClick(componentActionCallback);

        // creating all after-click statements
        componentActionCallback.forEachCall(call -> functionBody.appendStatement(
                expressionStatement(call)
        ));

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded(component -> functionBody.appendStatement(
                functionCall()
                        .target(
                                property(
                                        property(thisLiteral(), modelReloader.vueName()),
                                        name("notifyModelChanged")
                                )
                        )
                        .argument(stringLiteral(component.getCompositeId().getValue()))
                        .argument(
                                arrayElementGet()
                                        .target(property(property("response", "body"), name("model")))
                                        .element(stringLiteral(component.getCompositeId().getValue()))
                        )
        ));
    }

    @VueComponentFunction
    public void handleClick(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(property(thisLiteral(), licketRemote.vueName()), name("handleActionLinkClick")))
                                .argument(stringLiteral(getCompositeId().getValue()))
                                .argument(property(thisLiteral(), name("afterClick")))
                )
        );
    }

    protected void onAfterClick(ComponentActionCallback componentActionCallback) {}

    @Override
    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.addAttribute("v-on:click", "handleClick"));
    }

    public final void invokeAction(ComponentActionCallback componentActionCallback) {
        onClick();
        onAfterClick(componentActionCallback);
    }

    protected void onClick() {

    }
}

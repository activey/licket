package org.licket.semantic.component.modal;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.core.view.slot.AbstractSlottedLicketComponent;
import org.licket.framework.hippo.BlockBuilder;

/**
 * @author grabslu
 */
public abstract class AbstractSemanticUIModal extends AbstractSlottedLicketComponent<ModalSettings> {

    public AbstractSemanticUIModal(String id, ModalSettings modalSettings) {
        super(id, ModalSettings.class, ofModelObject(modalSettings), fromComponentClass(AbstractSemanticUIModal.class));
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(surfaceElement -> {
            surfaceElement.setAttribute("class", "ui modal");
        });
    }

    @VueComponentFunction
    public final void show(BlockBuilder body) {
        body.appendStatement(expressionStatement(
            functionCall().target(property("console", "log")).argument(stringLiteral("showing up modal"))));
    }

    @VueComponentFunction
    public final void hide(BlockBuilder body) {
        body.appendStatement(expressionStatement(
            functionCall().target(property("console", "log")).argument(stringLiteral("hiding popup"))));
    }

    @OnVueMounted
    public final void initializeModal(BlockBuilder body) {
        body.appendStatement(expressionStatement(
            functionCall().target(property("console", "log")).argument(stringLiteral("initializing popup"))));
    }
}

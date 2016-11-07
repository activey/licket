package org.licket.semantic.component.modal;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author grabslu
 */
public abstract class AbstractSemanticUIModal extends AbstractLicketComponent<ModalSettings> {

    public AbstractSemanticUIModal(String id, ModalSettings modalSettings) {
        super(id, ModalSettings.class, ofModelObject(modalSettings),
            fromComponentClass(AbstractSemanticUIModal.class));
    }

    protected void onBeforeRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(surfaceElement -> {
            surfaceElement.addAttribute("class", "ui modal");
        });
    }

    @VueComponentFunction
    public final void show(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticModal())
                        .argument(stringLiteral("show"))
        ));
    }

    @VueComponentFunction
    public final void hide(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticModal())
                        .argument(stringLiteral("hide"))
        ));
    }

    @OnVueMounted
    public final void initializeModal(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticModal())
                        .argument(objectLiteral())
        ));
    }

    private PropertyNameBuilder semanticModal() {
        return property(
                functionCall()
                        .target(name("$"))
                        .argument(property(thisLiteral(), name("$el"))),
                name("modal")
        );
    }

    public final FunctionCallBuilder callShow(LicketComponent<?> caller) {
        // TODO check if caller and modal has the same parent, if not - call is not possible.
        return functionCall().target(
                property(
                        arrayElementGet()
                                .target(property(property("this", "$parent"), "$refs"))
                                .element(getId()),
                        name("show")
                ));
    }
}

package org.licket.semantic.component.modal;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.link.ComponentFunctionCallback;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
public abstract class AbstractSemanticUIModal extends AbstractLicketMultiContainer<ModalSettings> {

    private ModalSection headerContainer;
    private ModalSection bodyContainer;
    private ModalSection actionsContainer;

    public AbstractSemanticUIModal(String id, ModalSettings modalSettings, LicketComponentModelReloader modelReloader) {
        super(id, ModalSettings.class, ofModelObject(modalSettings), fromComponentClass(AbstractSemanticUIModal.class), modelReloader);
    }

    @Override
    protected final void onInitializeContainer() {
        add(new ModalSection("header-section", modelReloader()) {
            @Override
            protected void onInitializeContainer() {
                onInitializeHeader(this, "content-block");
            }
        });

        add(new ModalSection("main-section", modelReloader()) {
            @Override
            protected void onInitializeContainer() {
                onInitializeBody(this, "content-block");
            }
        });

        add(new ModalSection("actions-section", modelReloader()) {
            @Override
            protected void onInitializeContainer() {
                onInitializeActions(this, "content-block");
            }
        });
    }

    protected void onInitializeHeader(ModalSection modalSection, String contentId) {}

    protected void onInitializeBody(ModalSection content, String contentId) {}

    protected void onInitializeActions(ModalSection content, String contentId) {}

    protected void onRenderContainer(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(surfaceElement -> surfaceElement.addAttribute("class", "ui modal"));
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

    @Override
    public SemanticUIModalAPI api(ComponentFunctionCallback componentFunctionCallback) {
        return new SemanticUIModalAPI(this, componentFunctionCallback);
    }
}

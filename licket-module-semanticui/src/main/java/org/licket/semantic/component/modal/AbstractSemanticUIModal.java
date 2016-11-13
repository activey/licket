package org.licket.semantic.component.modal;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.core.view.tree.LicketComponentTreeWalkSequence.source;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.AbstractReloadableLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

import java.util.function.Predicate;

/**
 * @author grabslu
 */
public abstract class AbstractSemanticUIModal extends AbstractReloadableLicketComponent<ModalSettings> {

    private ModalSection headerContainer;
    private ModalSection bodyContainer;
    private ModalSection actionsContainer;

    public AbstractSemanticUIModal(String id, ModalSettings modalSettings, LicketComponentModelReloader modelReloader) {
        super(id, ModalSettings.class, ofModelObject(modalSettings), fromComponentClass(AbstractSemanticUIModal.class), modelReloader);
    }

    @Override
    protected final void onInitialize() {
        this.bodyContainer = new ModalSection("main-section", modelReloader());
        bodyContainer.setParent(this);
        onInitializeBody(bodyContainer, "content-block");
        bodyContainer.initialize();
    }

    protected void onInitializeHeader(ModalSection modalSection, String contentId) {}

    protected void onInitializeBody(ModalSection content, String contentId) {}

    protected void onInitializeActions(ModalSection content, String contentId) {}

    @Override
    public void traverseDown(Predicate<LicketComponent<?>> componentConsumer) {
        if (componentConsumer.test(bodyContainer)) {
            bodyContainer.traverseDown(componentConsumer);
        }
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
        return functionCall().target(
                property(
                        source(caller).target(this).traverseSequence(),
                        name("show")
                ));
    }

    public final FunctionCallBuilder callHide(LicketComponent<?> caller) {
        return functionCall().target(
                property(
                        source(caller).target(this).traverseSequence(),
                        name("hide")
                ));
    }
}

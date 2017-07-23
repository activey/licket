package org.licket.semantic.component.dimmer;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
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
public abstract class AbstractSemanticUIDimmer extends AbstractLicketMultiContainer<DimmerSettings> {

    private final LicketComponentModelReloader modelReloader;

    public AbstractSemanticUIDimmer(String id, DimmerSettings dimmerSettings, LicketComponentModelReloader modelReloader) {
        super(id, DimmerSettings.class, ofModelObject(dimmerSettings), fromComponentClass(AbstractSemanticUIDimmer.class));
        this.modelReloader = modelReloader;
    }

    @Override
    protected final void onInitializeContainer() {
        add(new SemanticUIDimmerContainer("dimmer-container", getComponentModel().get(), modelReloader) {
            @Override
            protected void onInitializeContainer() {
                add(new DimmerContent("content-section", modelReloader) {
                    @Override
                    protected void onInitializeContainer() {
                        onInitializeContent(this, "content-block");
                    }
                });
            }
        });
    }

    protected void onInitializeContent(DimmerContent dimmerContent, String contentId) {}

    @VueComponentFunction
    public final void show(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticDimmer())
                        .argument(stringLiteral("show"))
        ));
    }

    @VueComponentFunction
    public final void hide(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticDimmer())
                        .argument(stringLiteral("hide"))
        ));
    }

    @OnVueMounted
    public final void initializeDimmer(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(semanticDimmer())
                        .argument(objectLiteral())
        ));
    }

    private PropertyNameBuilder semanticDimmer() {
        return property(
                functionCall()
                        .target(name("$"))
                        .argument(property(thisLiteral(), name("$el"))),
                name("dimmer")
        );
    }

    @Override
    public SemanticUIDimmerAPI api(ComponentFunctionCallback componentFunctionCallback) {
        return new SemanticUIDimmerAPI(this, componentFunctionCallback);
    }

    @Override
    public final LicketComponentModelReloader getModelReloader() {
        return modelReloader;
    }
}

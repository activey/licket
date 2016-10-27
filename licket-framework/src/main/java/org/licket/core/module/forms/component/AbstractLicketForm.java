package org.licket.core.module.forms.component;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.ComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.annotation.VueComponent;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.*;

/**
 * @author activey
 */
@VueComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketContainer<T> {

    public AbstractLicketForm(String id, Class<T> modelClass, LicketModel<T> model, ComponentView componentView,
                              LicketRemoteCommunication communicationService,
                              LicketComponentModelReloader modelReloader) {
        super(id, modelClass, model, componentView, modelReloader);
    }

    public final void submitForm(T formModelObject, ComponentActionCallback actionCallback) {
        setComponentModel(ofModelObject(formModelObject));
        onSubmit();
        onAfterSubmit(actionCallback);
    }

    protected void onSubmit() {}

    @Override
    protected void onRenderContainer(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            // TODO check if element is in fact a <form>
            element.setAttribute("v-on:submit", "submitForm");
        });
    }

    @VueComponentFunction
    public void afterSubmit(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        // setting current form model directly without event emitter
        functionBody
            .appendStatement(
                expressionStatement(assignment().left(property(thisLiteral(), name("model")))
                    .right(arrayElementGet()
                        .target(
                            property(functionCall().target(property(name("response"), name("json"))), name("model")))
                        .element(stringLiteral(getCompositeId().getValue())))));

        // gathering all others
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterSubmit(componentActionCallback);

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded(component -> {
            functionBody.appendStatement(reloadComponent(component));
        });
    }

    private ExpressionStatementBuilder reloadComponent(LicketComponent<?> component) {
        return expressionStatement(functionCall().target(property(name("modelReloader"), name("notifyModelChanged")))
                .argument(stringLiteral(component.getCompositeId().getValue()))
                .argument(arrayElementGet()
                        .target(
                                property(functionCall().target(property(name("response"), name("json"))), name("model")))
                        .element(stringLiteral(component.getCompositeId().getValue()))));
    }

    protected void onAfterSubmit(ComponentActionCallback componentActionCallback) {}

    @VueComponentFunction
    public void submitForm(BlockBuilder functionBlock) {
        functionBlock
            .appendStatement(expressionStatement(functionCall()
                .target(property(name("$licketRemote"), name("submitForm")))
                .argument(stringLiteral(getCompositeId().getValue())).argument(property(thisLiteral(), name("model")))
                .argument(functionNode().param(name("response")).body(block().appendStatement(
                        expressionStatement(
                                functionCall()
                                        .target(property(name("vm"), name("afterSubmit")))
                                        .argument(name("response"))
                        )
                )))))
            .appendStatement(ReturnStatementBuilder.returnStatement().returnValue(name("false")));
    }
}

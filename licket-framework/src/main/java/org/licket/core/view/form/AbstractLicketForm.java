package org.licket.core.view.form;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.annotation.VueComponent;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ExpressionStatementBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.surface.element.SurfaceElement;

/**
 * @author activey
 */
@VueComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketMultiContainer<T> {

    private LicketRemote licketRemote;
    private LicketComponentModelReloader modelReloader;

    public AbstractLicketForm(String id, Class<T> modelClass, LicketComponentModel<T> model, LicketComponentView componentView,
                              LicketRemote licketRemote,
                              LicketComponentModelReloader modelReloader) {
        super(id, modelClass, model, componentView, modelReloader);
        this.licketRemote = checkNotNull(licketRemote, "Liket remote instance must not be null!");
        this.modelReloader = checkNotNull(modelReloader, "Model reloader instance must not be null!");
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
            // TODO check if element is in fact a <form>, or not necessary?
            element.addAttribute("v-on:submit", "submitForm");
        });
    }

    @Override
    protected void onElementReplaced(SurfaceElement surfaceElement) {
        super.onElementReplaced(surfaceElement);
    }

    @VueComponentFunction
    public void afterSubmit(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        // setting current form model directly without event emitter
        functionBody
            .appendStatement(
                expressionStatement(assignment().left(property(thisLiteral(), name("model")))
                    .right(arrayElementGet()
                        .target(
                            property(property("response", "body"), "model"))
                        .element(stringLiteral(getCompositeId().getValue())))));

        // gathering all others
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterSubmit(componentActionCallback);

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded(component -> {
            functionBody.appendStatement(reloadComponent(component));
        });
        // invoking javascript calls
        componentActionCallback.forEachCall(call -> functionBody.appendStatement(
                expressionStatement(call)
        ));
    }

    private ExpressionStatementBuilder reloadComponent(LicketComponent<?> component) {
        return expressionStatement(functionCall().target(property(property(thisLiteral(), modelReloader.vueName()), name("notifyModelChanged")))
                .argument(stringLiteral(component.getCompositeId().getValue()))
                .argument(arrayElementGet()
                        .target(property(property("response", "body"), name("model")))
                        .element(stringLiteral(component.getCompositeId().getValue()))));
    }

    protected void onAfterSubmit(ComponentActionCallback componentActionCallback) {}

    @VueComponentFunction
    public void submitForm(BlockBuilder functionBlock) {
        functionBlock
            .appendStatement(expressionStatement(
                    licketRemote.callSubmitForm(
                            getCompositeId().getValue(), property(thisLiteral(), name("afterSubmit"))
                    )
            ))
            .appendStatement(returnStatement().returnValue(name("false")));
    }
}

package org.licket.core.module.forms.component;

import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.ComponentView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.AngularComponent;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ReturnStatementBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketModel.ofModelObject;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
@AngularComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketContainer<T> {

    @Autowired
    @Name("licketRemote")
    private LicketRemoteCommunication communicationService;

    public AbstractLicketForm(String id, Class<T> modelClass, LicketModel<T> model, ComponentView componentView) {
        super(id, modelClass, model, componentView);
    }

    public final void submitForm(T formModelObject) {
        setComponentModel(ofModelObject(formModelObject));
        onSubmit();
    }

    protected void onSubmit() {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            // TODO check if element is in fact a <form>
            element.setAttribute("(submit)", "submitForm()");
        });
    }

    @AngularClassFunction
    public void handleResponse(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        functionBody.appendStatement(expressionStatement(assignment()
                .left(property(thisLiteral(), name("model")))
                .right(response)));
    }

    @AngularClassFunction
    public void submitForm(BlockBuilder functionBlock) {
        functionBlock
            .appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("licketRemote"), name("invokeComponentAction")))
                                .argument(stringLiteral("submitForm"))
                                .argument(componentData())
                                .argument(property(thisLiteral(), name("handleResponse")))
                )
        ).appendStatement(ReturnStatementBuilder.returnStatement().returnValue(name("false")));
    }

    private ObjectLiteralBuilder componentData() {
        return objectLiteral()
                .objectProperty(
                        propertyBuilder()
                                .name("compositeId")
                                .value(stringLiteral(getCompositeId().getValue())))
                .objectProperty(
                        propertyBuilder()
                                .name("data")
                                .value(property(thisLiteral(), name("model"))));
    }
}

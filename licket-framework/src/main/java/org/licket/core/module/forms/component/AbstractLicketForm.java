package org.licket.core.module.forms.component;

import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.ComponentView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.AngularComponent;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

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

    protected void onSubmit(T formModelObject) {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            element.setAttribute("(ngSubmit)", "submitForm()");
        });
    }

    @AngularClassFunction
    public void submitForm(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("licketRemote"), name("invokeComponentAction")))
                                .argument(property(thisLiteral(), name("model")))
                )
        );
    }
}

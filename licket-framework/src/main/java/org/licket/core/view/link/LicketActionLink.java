package org.licket.core.view.link;

import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class LicketActionLink extends AbstractLicketComponent<Void> {

    @Autowired
    @Name("licketRemote")
    private LicketRemoteCommunication communicationService;

    private LicketActionListener actionListener;

    public LicketActionLink(String id) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(LicketActionLink.class));
    }

    @AngularClassFunction
    public void doNothing(@Name("response") NameBuilder response, BlockBuilder functionBody) {
    }

    @AngularClassFunction
    public void invokeAction(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("licketRemote"), name("invokeComponentAction")))
                                .argument(stringLiteral("invokeAction"))
                                .argument(componentData())
                                .argument(property(thisLiteral(), name("doNothing")))
                )
        );
    }

    private ObjectLiteralBuilder componentData() {
        return objectLiteral()
                .objectProperty(
                        propertyBuilder()
                                .name("compositeId")
                                .value(stringLiteral(getCompositeId().getValue())));
    }

    public LicketActionLink actionListener(LicketActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)", "invokeAction()"));
    }

    public final void invokeAction() {
        if (actionListener != null) {
            actionListener.onInvokeAction();
        }
    }
}

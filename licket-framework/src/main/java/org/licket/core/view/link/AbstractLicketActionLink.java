package org.licket.core.view.link;

import static com.google.common.base.Preconditions.checkNotNull;
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

import com.google.common.base.Preconditions;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink extends AbstractLicketComponent<Void> {

    @Name("licketRemote")
    private LicketRemoteCommunication communicationService;

    public AbstractLicketActionLink(String id, LicketRemoteCommunication communicationService) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(AbstractLicketActionLink.class));
        this.communicationService = checkNotNull(communicationService, "Communication service reference must not be null!");
    }

    @AngularClassFunction
    public void doNothing(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        // as it says, does nothing ;)
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

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)", "invokeAction()"));
    }

    public final void invokeAction() {
        // testing testing testing
        Something something = new Something();

        onInvokeAction(something);
    }

    protected abstract void onInvokeAction(Something something);

}

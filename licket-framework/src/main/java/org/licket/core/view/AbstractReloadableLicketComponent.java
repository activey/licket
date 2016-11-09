package org.licket.core.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.OnVueCreated;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
public abstract class AbstractReloadableLicketComponent<T> extends AbstractLicketComponent<T> {

    @Name("model")
    private ObjectLiteralBuilder modelProperty;
    
    private final LicketComponentModelReloader modelReloader;

    public AbstractReloadableLicketComponent(String id, Class<T> modelClass, LicketComponentModelReloader modelReloader) {
        super(id, modelClass);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractReloadableLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    public AbstractReloadableLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view,
                                   LicketComponentModelReloader modelReloader) {
        super(id, modelClass, componentModel, view);
        this.modelReloader = checkNotNull(modelReloader, "Model reloader has to be not null!");
    }

    @VueComponentFunction
    public void handleModelChanged(@Name("changedModelData") NameBuilder changedModelData, BlockBuilder functionBody) {
        functionBody.appendStatement(
                expressionStatement(
                        ifStatement()
                                .condition(equalCheckExpression()
                                        .left(property(changedModelData, name("compositeId")))
                                        .right(stringLiteral(getCompositeId().getValue())))
                                .then(
                                        assignment()
                                                .left(property(name("this"), name("model")))
                                                .right(property(changedModelData, name("model")))
                                ))
        );
    }

    @OnVueCreated
    public void onVueCreated(BlockBuilder body) {
        body.appendStatement(expressionStatement(
                functionCall()
                        .target(property(property(thisLiteral(), modelReloader.vueName()), name("listenForModelChange")))
                        .argument(property(thisLiteral(), name("handleModelChanged")))
        ));
    }

    protected final LicketComponentModelReloader modelReloader() {
        return modelReloader;
    }
}

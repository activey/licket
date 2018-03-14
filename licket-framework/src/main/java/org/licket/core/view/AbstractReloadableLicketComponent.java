package org.licket.core.view;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.annotation.OnVueCreated;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.EqualCheckExpressionBuilder.equalCheckExpression;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.IfStatementBuilder.ifStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.KeywordLiteralBuilder.trueLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.NotEqualCheckExpressionBuilder.notEqualCheckExpression;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.ReturnStatementBuilder.returnStatement;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author grabslu
 */
public abstract class AbstractReloadableLicketComponent<T> extends AbstractLicketComponent<T> {

  @Name("model")
  private ObjectLiteralBuilder modelProperty;

  public AbstractReloadableLicketComponent(String id, Class<T> modelClass) {
    super(id, modelClass);
  }

  public AbstractReloadableLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel) {
    super(id, modelClass, componentModel);
  }

  public AbstractReloadableLicketComponent(String id, Class<T> modelClass, LicketComponentModel<T> componentModel, LicketComponentView view) {
    super(id, modelClass, componentModel, view);
  }

  @VueComponentFunction
  public final void handleModelChanged(@Name("changedModelData") NameBuilder changedModelData, @Name("patch") NameBuilder patch, BlockBuilder functionBody) {
    functionBody.appendStatement(
            expressionStatement(
                    ifStatement()
                            .condition(notEqualCheckExpression()
                                    .left(property(changedModelData, name("compositeId")))
                                    .right(stringLiteral(getCompositeId().getValue())))
                            .then(
                                    returnStatement()
                            ))
    );

    functionBody.appendStatement(ifStatement()
            .condition(equalCheckExpression()
                    .left(patch)
                    .right(trueLiteral())
            )
            .then(block()
                    .appendStatement(expressionStatement(applyPatchFunction(changedModelData)))
                    .appendStatement(returnStatement())));

    functionBody.appendStatement(
            expressionStatement(assignment()
                    .left(property(name("this"), name("model")))
                    .right(property(changedModelData, name("model"))))
    );
  }

  private FunctionCallBuilder applyPatchFunction(NameBuilder changedModelData) {
    return functionCall()
            .target(property("jsonpatch", "applyPatch"))
            .argument(property(thisLiteral(), name("model")))
            .argument(property(changedModelData, name("patch")));
  }

  @OnVueCreated
  public final void onVueCreated(BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(property(property(thisLiteral(), LicketComponentModelReloader.serviceName()), name("listenForModelChange")))
                    .argument(property(thisLiteral(), name("handleModelChanged")))
    ));
  }
}

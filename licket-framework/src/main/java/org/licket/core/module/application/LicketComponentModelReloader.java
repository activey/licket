package org.licket.core.module.application;

import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.NewExpressionBuilder.newExpression;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.NewExpressionBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author grabslu
 */
public class LicketComponentModelReloader implements VueClass {

    @Name("modelChangedEventEmitter")
    private NewExpressionBuilder modelChangedEventEmitter = newExpression()
        .target(property(property(name("ng"), name("core")), name("EventEmitter"))).argument(name("true"));

    @VueComponentFunction
    public void listenForModelChange(@Name("changeListener") NameBuilder changeListener, BlockBuilder body) {
        body.appendStatement(expressionStatement(functionCall()
            .target(property(property(thisLiteral(), name("modelChangedEventEmitter")), name("subscribe")))
            .argument(changeListener)));
    }

    @VueComponentFunction
    public void notifyModelChanged(@Name("compositeId") NameBuilder compositeId,
                                   @Name("changedModelData") NameBuilder changedModelData, BlockBuilder body) {
        body.appendStatement(expressionStatement(
            functionCall().target(property(property(thisLiteral(), name("modelChangedEventEmitter")), name("emit")))
                .argument(changedModelEventData(compositeId, changedModelData))));
    }

    private ObjectLiteralBuilder changedModelEventData(NameBuilder compositeId, NameBuilder changedModelData) {
        return objectLiteral()
                .objectProperty(propertyBuilder().name("compositeId").value(compositeId))
                .objectProperty(propertyBuilder().name("model").value(changedModelData));
    }

    @Override
    public NameBuilder vueName() {
        return name("$licketModelReloader");
    }
}

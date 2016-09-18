package org.licket.core.resource.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class AngularBootApplicationJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private LicketApplication licketApplication;

    @Override
    public String getName() {
        return "Licket.boot.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.statement(expressionStatement(functionCall()
                .argument(domLoadedEventName())
                .argument(functionNode().body(bootstrapAngularLogic()))
                .target(property(name("document"), name("addEventListener")))));
    }

    private StringLiteralBuilder domLoadedEventName() {
        return stringLiteral("DOMContentLoaded");
    }

    private BlockBuilder bootstrapAngularLogic() {
        return  block().statement(functionCall()
                .argument(property(name("app"), rootComponentName()))
                .target(property(property(property(name("ng"), name("platform")), name("browser")), name("bootstrap"))));
    }

    private NameBuilder rootComponentName() {
        // TODO think about it little bit more and refactor
        return name(licketApplication.getRootComponentContainer().getCompositeId().getNormalizedValue());
    }

}

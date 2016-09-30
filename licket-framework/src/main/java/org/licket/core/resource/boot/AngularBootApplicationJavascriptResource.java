package org.licket.core.resource.boot;

import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;
import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

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
        scriptBlockBuilder.prependStatement(expressionStatement(
            functionCall().argument(domLoadedEventName()).argument(functionNode().body(bootstrapAngularLogic()))
                .target(property(name("document"), name("addEventListener")))));
    }

    private StringLiteralBuilder domLoadedEventName() {
        return stringLiteral("DOMContentLoaded");
    }

    private BlockBuilder bootstrapAngularLogic() {
        return block().prependStatement(
            expressionStatement(functionCall().argument(property(name("app"), applicationModuleName()))
                .target(property(
                    functionCall().target(
                        property(property(name("ng"), name("platformBrowserDynamic")), name("platformBrowserDynamic"))),
                    name("bootstrapModule")))));
    }

    private NameBuilder applicationModuleName() {
        return name("AppModule");
    }

}

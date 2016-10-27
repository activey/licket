package org.licket.core.module.application.resource;

import org.licket.core.module.application.ApplicationModulePlugin;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ApplicationModulePluginResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private ApplicationModulePlugin applicationModulePlugin;

    @Override
    public String getName() {
        return "vue-plugin-application-module.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.appendStatement(
                expressionStatement(
                        assignment()
                                .left(applicationModulePlugin.vueName())
                                .right(pluginInitializer())
                )
        );
    }

    private FunctionNodeBuilder pluginInitializer() {
        // TODO generate list of services from module
        applicationModulePlugin.

        return functionNode()
                .param(name("Vue"))
                .param(name("options"))
                .body(
                        block().appendStatement(
                                functionCall()
                                        .target(property("Object", "defineProperties"))
                                        .argument(property("Vue", "prototype"))
                                        .argument(objectLiteral())
                        )
                );
    }
}

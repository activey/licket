package org.licket.core.module.application.resource;

import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.*;

import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.NewExpressionBuilder.newExpression;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
public class ApplicationEventHubResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Override
    public String getName() {
        return "application-event-hub.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.appendStatement(expressionStatement(
                assignment()
                        .left(property("app", "ApplicationEventHub"))
                        .right(newExpression().target(name("Vue")))
        ));
    }
}

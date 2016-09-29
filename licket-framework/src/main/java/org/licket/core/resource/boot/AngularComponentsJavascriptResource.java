package org.licket.core.resource.boot;

import static org.licket.core.view.hippo.testing.ngclass.AngularClassStructureDecorator.fromAngularClass;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author activey
 */
public class AngularComponentsJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    @Qualifier("applicationModule")
    public AngularModule applicationModule;
    @Autowired
    private LicketApplication licketApplication;

    @Override
    public String getName() {
        return "Licket.components.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        licketApplication.modules().forEach(module -> module.classes().forEach(angularClass -> {
            scriptBlockBuilder
                .appendStatement(expressionStatement(fromAngularClass(angularClass).decorate(assignment())));
        }));
    }
}

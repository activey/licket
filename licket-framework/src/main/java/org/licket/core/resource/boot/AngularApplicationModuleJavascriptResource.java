package org.licket.core.resource.boot;

import static org.licket.core.view.hippo.testing.ngclass.AngularClassStructureDecorator.fromAngularClass;
import static org.licket.core.view.hippo.testing.ngmodule.AngularModuleStructureDecorator.fromAngularModule;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author activey
 */
public class AngularApplicationModuleJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private LicketApplication application;

    @Autowired
    @Qualifier("applicationModule")
    private AngularModule applicationModule;

    @Autowired
    @Qualifier("applicationModule")
    private AngularClass applicationClass;

    @Override
    public String getName() {
        return "Licket.application.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.appendStatement(
                expressionStatement(
                        fromAngularModule(applicationModule, application)
                                .decorate(fromAngularClass(applicationClass))
                )
        );
    }
}

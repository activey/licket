package org.licket.core.resource.boot;

import static com.google.common.base.Predicates.instanceOf;
import static org.licket.core.view.hippo.ngclass.AngularClassStructureDecorator.fromAngularClass;
import static org.licket.core.view.hippo.ngcomponent.AngularComponentStructureDecorator.fromLicketComponent;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.ngmodule.AngularModule;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author activey
 */
public class AngularComponentsJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    @Qualifier("applicationModule")
    private AngularModule applicationModule;

    @Override
    public String getName() {
        return "Licket.components.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        applicationModule.classes().forEach(angularClass -> {
            // TODO not really nice, rework ...
            if (instanceOf(LicketComponent.class).apply(angularClass)) {
                // decorating ng.core.Class with ng.core.Component
                LicketComponent<?> licketComponent = (LicketComponent<?>) angularClass;
                scriptBlockBuilder.appendStatement(
                    expressionStatement(fromLicketComponent(licketComponent).decorate(fromAngularClass(angularClass))));
                return;
            }
            // just simple ng.core.Class
            scriptBlockBuilder
                .prependStatement(expressionStatement(fromAngularClass(angularClass).decorate(assignment())));
        });
    }
}

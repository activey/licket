package org.licket.core.resource.boot;

import static org.licket.core.view.hippo.testing.ngclass.AngularClassStructureDecorator.fromAngularClass;
import static org.licket.core.view.hippo.testing.ngmodule.AngularModuleStructureDecorator.fromAngularModule;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.hippo.testing.ngclass.AngularClass;
import org.licket.core.view.hippo.testing.ngclass.AngularClassStructureDecorator;
import org.licket.core.view.hippo.testing.ngmodule.AngularModule;
import org.licket.core.view.hippo.testing.ngmodule.AngularModuleStructureDecorator;
import org.licket.framework.hippo.*;
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
        AngularModuleStructureDecorator moduleStructureDecorator = fromAngularModule(applicationModule);
        AngularClassStructureDecorator moduleClassStructureDecorator = fromAngularClass(applicationClass);


        scriptBlockBuilder.appendStatement(expressionStatement(moduleClassStructureDecorator.decorate(assignment())));

        if (1 == 1) {
            return;
        }
        scriptBlockBuilder.appendStatement(expressionStatement(assignment()
            .left(applicationModule.angularName())
            .right(
                    functionCall()
                            .target(property(ngModuleDeclaration(), name("Class")))
                            .argument(objectLiteral()
                                        .objectProperty(
                                                propertyBuilder()
                                                        .name(name("constructor"))
                                                        .value(functionNode().body(block())))
                            )
            )
        ));
    }

    private PropertyNameBuilder applicationModuleName() {
        return property(name("app"), name("AppModule"));
    }

    private FunctionCallBuilder ngModuleDeclaration() {
        return functionCall().target(property(property(name("ng"), name("core")), name("NgModule")))
            .argument(
                    objectLiteral()
                            .objectProperty(propertyBuilder().name("imports").arrayValue(moduleImports()))
                            .objectProperty(propertyBuilder().name("declarations").arrayValue(declarations()))
                            .objectProperty(propertyBuilder().name("bootstrap").arrayValue(bootstrapComponents())));
    }

    private ArrayLiteralBuilder moduleImports() {
        ArrayLiteralBuilder arrayLiteralBuilder = arrayLiteral();
        application.modules().forEach(applicationModule -> arrayLiteralBuilder.element(applicationModule.angularName()));
        return arrayLiteralBuilder;
    }

    private ArrayLiteralBuilder declarations() {
        ArrayLiteralBuilder arrayLiteralBuilder = arrayLiteral();
        application.traverseDownContainers(container -> {
            if (!container.getComponentContainerView().isExternalized()) {
                return true;
            }
            arrayLiteralBuilder.element(property(name("app"), name(container.getCompositeId().getNormalizedValue())));
            return true;
        });
        return arrayLiteralBuilder;
    }

    private ArrayLiteralBuilder bootstrapComponents() {
        return arrayLiteral().element(
            property(name("app"), name(application.rootComponentContainer().getCompositeId().getNormalizedValue())));
    }
}

package org.licket.core.resource.boot;

import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author activey
 */
public class AngularApplicationModuleJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private LicketApplication application;

    @Override
    public String getName() {
        return "Licket.app.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.appendStatement(expressionStatement(assignment()
            .left(property(name("app"), name("AppModule")))
            .right(functionCall().target(property(ngModuleDeclaration(), name("Class"))).argument(objectLiteral()
                .objectProperty(propertyBuilder().name(name("constructor")).value(functionNode().body(block())))))));
    }

    private FunctionCallBuilder ngModuleDeclaration() {
        return functionCall().target(property(property(name("ng"), name("core")), name("NgModule")))
            .argument(objectLiteral().objectProperty(propertyBuilder().name("imports").arrayValue(moduleImports()))
                .objectProperty(propertyBuilder().name("declarations").arrayValue(declarations()))
                .objectProperty(propertyBuilder().name("bootstrap").arrayValue(bootstrapComponents())));
    }

    private ArrayLiteralBuilder moduleImports() {
        return arrayLiteral().element(property(property(name("ng"), name("platformBrowser")), name("BrowserModule")))
            .element(property(property(name("ng"), name("http")), name("HttpModule")));
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
            property(name("app"), name(application.getRootComponentContainer().getCompositeId().getNormalizedValue())));
    }
}

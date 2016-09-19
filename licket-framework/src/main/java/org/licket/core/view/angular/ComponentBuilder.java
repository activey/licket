package org.licket.core.view.angular;

import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.mozilla.javascript.ast.ExpressionStatement;

import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyGetBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class ComponentBuilder extends AbstractAstNodeBuilder<ExpressionStatement> {

    private String selectorValue;
    private String templateUrl;
    private List<String> dependencies = newLinkedList();
    private ComponentClassBuilder classBuilder;
    private String name;

    private ComponentBuilder() {}

    public static ComponentBuilder component() {
        return new ComponentBuilder();
    }

    public ComponentBuilder clazz(ComponentClassBuilder classBuilder) {
        this.classBuilder = classBuilder;
        return this;
    }

    public ComponentBuilder selector(String selectorValue) {
        this.selectorValue = selectorValue;
        return this;
    }

    public ComponentBuilder templateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
        return this;
    }

    public ComponentBuilder componentName(String name) {
        this.name = name;
        return this;
    }

    public void componentDependency(LicketComponentContainer<?> child) {
        dependencies.add(child.getCompositeId().getNormalizedValue());
    }

    @Override
    public ExpressionStatement build() {
        ExpressionStatement expressionStatement = expressionStatement(assignment()
                .left(property(name("app"), name(name)))
                .right(functionCall()
                        .target(property(functionCall()
                                .target(property(property(name("ng"), name("core")), name("Component")))
                                .argument(componentSettings())
                        ,name("Class")))
                        .argument(classBuilder)))
                .build();
        return expressionStatement;
    }

    private ObjectLiteralBuilder componentSettings() {
        ObjectLiteralBuilder literalBuilder = objectLiteral()
                .objectProperty(propertyBuilder().name(name("selector")).value(stringLiteral(selectorValue)))
                .objectProperty(propertyBuilder().name(name("templateUrl")).value(stringLiteral(templateUrl)));
        if (dependencies.size() > 0) {
            return literalBuilder.objectProperty(propertyBuilder().name(name("directives")).value(dependencies()));
        }
        return literalBuilder;
    }

    private ArrayLiteralBuilder dependencies() {
        ArrayLiteralBuilder arrayLiteralBuilder = arrayLiteral();
        dependencies.forEach(dependencyId -> arrayLiteralBuilder.element(name("app." + dependencyId)));
        return arrayLiteralBuilder;
    }
}

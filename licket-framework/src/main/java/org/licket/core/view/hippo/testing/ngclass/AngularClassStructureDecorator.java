package org.licket.core.view.hippo.testing.ngclass;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.licket.framework.hippo.ArrayLiteralBuilder.arrayLiteral;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import java.lang.reflect.Method;
import java.util.Optional;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.framework.hippo.AssignmentBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class AngularClassStructureDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AngularClassStructureDecorator.class);
    private AngularClass angularClass;

    private AngularClassStructureDecorator(AngularClass angularClass) {
        this.angularClass = angularClass;
    }

    public static AngularClassStructureDecorator fromAngularClass(AngularClass angularClass) {
        return new AngularClassStructureDecorator(angularClass);
    }

    public final AssignmentBuilder decorate(AssignmentBuilder assignment) {
        return assignment
                .left(angularClass.angularName())
                .right(functionCall()
                        .target(createClassFunctionCall())
                        .argument(classDefinition()));
    }

    private PropertyNameBuilder createClassFunctionCall() {
        return property(property(name("ng"), name("core")), name("Class"));
    }

    private ObjectLiteralBuilder classDefinition() {
        ObjectLiteralBuilder definition = objectLiteral();

        // function constructor definition
        BlockBuilder constructorFunctionBody = block();
        // declaring all member functions
        stream(angularClass.getClass().getDeclaredMethods())
            .forEach(method -> writeMemberFunctionBody(constructorFunctionBody, method));

        // declaring constructor
        definition.objectProperty(propertyBuilder().name("constructor")
            .arrayValue(arrayLiteral()
                // .element() ... dependencies
                .element(functionNode().body(constructorFunctionBody))));
        return definition;
    }

    private void writeMemberFunctionBody(BlockBuilder constructorFunctionBody, Method method) {
        Optional<AngularClassFunction> classFunction = getClassFunction(method);
        if (!classFunction.isPresent()) {
            LOGGER.trace("Skipping processing {} method.", method.getName());
            return;
        }
        // declaring member function
        AngularClassMemberFunction memberFunction = new AngularClassMemberFunction();

        // appending statement
        constructorFunctionBody
            .appendStatement(expressionStatement(
                    assignment()
                        .left(property(thisLiteral(),
                            name(firstNonNull(trimToNull(classFunction.get().value()), method.getName()))))
                        .right(memberFunction.toFunctionNode(method, angularClass))));
    }

    private Optional<AngularClassFunction> getClassFunction(Method method) {
        AngularClassFunction angularClassFunction = method.getAnnotation(AngularClassFunction.class);
        if (isPrivate(method.getModifiers())) {
            LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
            return empty();
        }
        return ofNullable(angularClassFunction);
    }
}
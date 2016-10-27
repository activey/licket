package org.licket.core.view.hippo.angular.ngclass;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.core.view.hippo.angular.ngclass.AngularClassPropertiesDecorator.fromAngularClassProperties;
import static org.licket.core.view.hippo.angular.ngclass.AngularInjectablesDecorator.forAngularClassDependencies;
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
import org.licket.core.view.hippo.angular.AngularStructuralDecorator;
import org.licket.core.view.hippo.angular.annotation.AngularClassConstructor;
import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.core.view.hippo.vue.extend.VueExtendMemberFunction;
import org.licket.framework.hippo.AssignmentBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionCallBuilder;
import org.licket.framework.hippo.NameBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class AngularClassStructureDecorator implements AngularStructuralDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AngularClassStructureDecorator.class);
    private VueClass vueClass;

    private AngularClassStructureDecorator(VueClass vueClass) {
        this.vueClass = vueClass;
    }

    public static AngularClassStructureDecorator fromAngularClass(VueClass vueClass) {
        return new AngularClassStructureDecorator(vueClass);
    }

    public final AssignmentBuilder decorate(AssignmentBuilder assignment) {
        return assignment.left(vueClass.vueName()).right(right());
    }

    private FunctionCallBuilder right() {
        return functionCall().target(createClassFunctionCall()).argument(body());
    }

    private PropertyNameBuilder createClassFunctionCall() {
        return property(property(name("ng"), name("core")), mostRight());
    }

    @Override
    public NameBuilder mostRight() {
        return name("Class");
    }

    @Override
    public ObjectLiteralBuilder body() {
        // function constructor definition, decorated with properties
        BlockBuilder constructorFunctionBody = fromAngularClassProperties(vueClass).decorate(block());
        // declaring all member functions
        stream(vueClass.getClass().getMethods())
            .forEach(method -> writeMemberFunctionBody(constructorFunctionBody, method));

        // analyzing @AngularClassConstructor annotation
        stream(vueClass.getClass().getMethods())
                .forEach(method -> writeClassConstructorBody(constructorFunctionBody, method));

        // declaring constructor
        AngularInjectablesDecorator injectablesDecorator = forAngularClassDependencies(vueClass);
        return objectLiteral().objectProperty(
                        propertyBuilder()
                                .name("constructor")
                                .arrayValue(injectablesDecorator.decorate(arrayLiteral())
                                    .element(injectablesDecorator.decorate(functionNode())
                                        .body(constructorFunctionBody))));
    }

    private void writeMemberFunctionBody(BlockBuilder constructorFunctionBody, Method method) {
        Optional<VueComponentFunction> classFunction = getClassFunction(method);
        if (!classFunction.isPresent()) {
            LOGGER.trace("Skipping processing {} method.", method.getName());
            return;
        }
        // declaring member function
        VueExtendMemberFunction memberFunction = new VueExtendMemberFunction();

        // appending statement
        constructorFunctionBody
            .appendStatement(expressionStatement(assignment()
                .left(property(thisLiteral(),
                    name(firstNonNull(trimToNull(classFunction.get().value()), method.getName()))))
                .right(memberFunction.toFunctionNode(method, vueClass))));
    }

    private Optional<VueComponentFunction> getClassFunction(Method method) {
        VueComponentFunction angularClassFunction = method.getAnnotation(VueComponentFunction.class);
        if (isPrivate(method.getModifiers())) {
            LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
            return empty();
        }
        return ofNullable(angularClassFunction);
    }

    private void writeClassConstructorBody(BlockBuilder constructorFunctionBody, Method method) {
        Optional<AngularClassConstructor> classConstructorFunction = getClassConstructorFunction(method);
        if (!classConstructorFunction.isPresent()) {
            LOGGER.trace("Skipping processing {} method.", method.getName());
            return;
        }
        // TODO rewrite this method...
        on(vueClass).call(method.getName(), constructorFunctionBody);
    }

    private Optional<AngularClassConstructor> getClassConstructorFunction(Method method) {
        AngularClassConstructor angularClassConstructorFunction = method.getAnnotation(AngularClassConstructor.class);
        if (isPrivate(method.getModifiers())) {
            LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
            return empty();
        }
        return ofNullable(angularClassConstructorFunction);
    }
}

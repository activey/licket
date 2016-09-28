package org.licket.core.view.hippo.testing.ngclass;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Predicates.*;
import static java.util.Arrays.stream;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.BlockBuilder.block;
import static org.licket.framework.hippo.FunctionNodeBuilder.functionNode;
import static org.licket.framework.hippo.NameBuilder.name;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.function.Consumer;

import com.google.common.base.Predicate;
import org.licket.core.consumer.IndexedConsumer;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author activey
 */
public class AngularClassMemberFunction {

    private static Predicate<Parameter> isOfType(Class<?> type) {
        return parameter -> assignableFrom(type).apply(parameter.getType());
    }

    private static Predicate<Parameter> hasNoAnnotations() {
        return parameter -> parameter.getAnnotations().length == 0;
    }

    private AbstractAstNodeBuilder<?>[] arguments;
    private FunctionNodeBuilder functionNode;
    private BlockBuilder body;

    public AngularClassMemberFunction() {
        initFunctionNode();
    }

    private void initFunctionNode() {
        functionNode = functionNode().body(body = block());
    }

    private Consumer<Parameter> readMethodParameter() {
        return new IndexedConsumer<Parameter>() {

            @Override
            protected void accept(Parameter parameter, int index) {
                if (isArgument(parameter)) {
                    NameBuilder functionArgument = parameterToArgument(parameter);
                    functionNode.param(functionArgument);
                    arguments[index] = functionArgument;
                    return;
                } else if (isBody(parameter)) {
                    arguments[index] = body;
                    return;
                }
                throw new AngularClassConstructionException("Parameter type not supported! %s", parameter.getName());
            }
        };
    }

    private NameBuilder parameterToArgument(Parameter parameter) {
        Name parameterName = parameter.getAnnotation(Name.class);
        if (parameterName == null) {
            return name(parameter.getName());
        }
        return name(firstNonNull(trimToNull(parameterName.value()), parameter.getName()));
    }

    private boolean isArgument(Parameter parameter) {
        return isOfType(NameBuilder.class).apply(parameter);
    }

    private boolean isBody(Parameter parameter) {
        return and(isOfType(BlockBuilder.class), hasNoAnnotations()).apply(parameter);
    }

    public FunctionNodeBuilder toFunctionNode(Method method, AngularClass angularClass) {
        readMethodMetadata(method);
        on(angularClass).call(method.getName(), arguments);

        return functionNode;
    }

    private void readMethodMetadata(Method method) {
        arguments = new AbstractAstNodeBuilder[method.getParameterCount()];
        stream(method.getParameters()).forEach(readMethodParameter());
    }
}

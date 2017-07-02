package org.licket.core.view.hippo.vue.extend;

import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.joor.Reflect.on;

import java.lang.reflect.Method;
import java.util.Optional;

import org.licket.core.view.hippo.vue.annotation.OnVueCreated;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.framework.hippo.BlockBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author activey
 */
public class OnVueMountedDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnVueMountedDecorator.class);
    private VueClass vueClass;

    private OnVueMountedDecorator(VueClass vueClass) {
        this.vueClass = vueClass;
    }

    public static OnVueMountedDecorator fromVueClass(VueClass vueClass) {
        return new OnVueMountedDecorator(vueClass);
    }

    public final BlockBuilder decorate(BlockBuilder constructorBody) {
        // analyzing @OnVueMounted annotation
        stream(vueClass.getClass().getMethods())
                .forEach(method -> writeClassConstructorBody(constructorBody, method));
        return constructorBody;
    }

    private void writeClassConstructorBody(BlockBuilder constructorFunctionBody, Method method) {
        Optional<OnVueMounted> onVueMounted = onVueMountedFunction(method);
        if (!onVueMounted.isPresent()) {
            return;
        }
        // TODO rewrite this method...
        on(vueClass).call(method.getName(), constructorFunctionBody);
    }

    private Optional<OnVueMounted> onVueMountedFunction(Method method) {
        OnVueMounted onVueCreatedFunction = method.getAnnotation(OnVueMounted.class);
        if (isPrivate(method.getModifiers())) {
            LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
            return empty();
        }
        return ofNullable(onVueCreatedFunction);
    }
}

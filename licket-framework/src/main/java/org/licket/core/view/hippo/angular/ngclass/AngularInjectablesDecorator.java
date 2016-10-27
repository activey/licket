package org.licket.core.view.hippo.angular.ngclass;

import static com.google.common.base.Predicates.assignableFrom;
import static com.google.common.base.Predicates.notNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.springframework.util.ReflectionUtils.doWithFields;
import java.lang.reflect.Field;

import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.core.view.hippo.vue.extend.VueClass;
import org.licket.framework.hippo.ArrayLiteralBuilder;
import org.licket.framework.hippo.FunctionNodeBuilder;
import org.springframework.util.ReflectionUtils;

/**
 * @author activey
 */
public class AngularInjectablesDecorator {

    private VueClass vueClass;

    private AngularInjectablesDecorator(VueClass vueClass) {
        this.vueClass = vueClass;
    }

    public static AngularInjectablesDecorator forAngularClassDependencies(VueClass vueClass) {
        return new AngularInjectablesDecorator(vueClass);
    }

    public ArrayLiteralBuilder decorate(ArrayLiteralBuilder arrayLiteral) {
        doWithFields(vueClass.getClass(), field -> doOnAccessible(field, accessibleField -> {
            AngularInjectable injectable = (AngularInjectable) accessibleField.get(vueClass);
            arrayLiteral.element(injectable.angularName());
        }), field -> notNull().apply(on(field).get()) && assignableFrom(AngularInjectable.class).apply(field.getType()));
        return arrayLiteral;
    }

    public FunctionNodeBuilder decorate(FunctionNodeBuilder functionNode) {
        doWithFields(vueClass.getClass(), field -> {
            Name customName = field.getAnnotation(Name.class);
            if (customName != null) {
                functionNode.param(name(customName.value()));
                return;
            }
            functionNode.param(name(field.getName()));
        }, field -> notNull().apply(on(field).get()) && assignableFrom(AngularInjectable.class).apply(field.getType()));
        return functionNode;
    }

    private void doOnAccessible(Field field, ReflectionUtils.FieldCallback fieldConsumer)
            throws IllegalAccessException {
        boolean previousAccessible = field.isAccessible();
        field.setAccessible(true);
        fieldConsumer.doWith(field);
        field.setAccessible(previousAccessible);
    }
}

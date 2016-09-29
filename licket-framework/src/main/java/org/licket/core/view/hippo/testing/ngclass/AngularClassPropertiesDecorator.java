package org.licket.core.view.hippo.testing.ngclass;

import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Predicates.assignableFrom;
import static com.google.common.base.Predicates.notNull;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.springframework.util.ReflectionUtils.doWithFields;

/**
 * @author activey
 */
public class AngularClassPropertiesDecorator {

    private AngularClass angularClass;

    public static AngularClassPropertiesDecorator fromAngularClassProperties(AngularClass angularClass) {
        return new AngularClassPropertiesDecorator(angularClass);
    }

    private AngularClassPropertiesDecorator(AngularClass angularClass) {
        this.angularClass = angularClass;
    }

    public ObjectLiteralBuilder decorate(ObjectLiteralBuilder objectLiteral) {
        doWithFields(angularClass.getClass(), field -> {
            doOnAccessible(field, accessibleField -> {
                ObjectPropertyBuilder objectProperty = (ObjectPropertyBuilder) accessibleField.get(angularClass);
                if (objectProperty == null) {
                    objectProperty = propertyBuilder().value(objectLiteral());
                }
                Name customName = accessibleField.getAnnotation(Name.class);
                if (customName != null) {
                    objectProperty.name(firstNonNull(trimToNull(customName.value()), accessibleField.getName()));
                } else {
                    objectProperty.name(accessibleField.getName());
                }
                objectLiteral.objectProperty(objectProperty);
            });
        }, field -> {
            return notNull().apply(on(field).get()) && assignableFrom(ObjectPropertyBuilder.class).apply(field.getType());
        });
        return objectLiteral;
    }

    private void doOnAccessible(Field field, ReflectionUtils.FieldCallback fieldConsumer)
            throws IllegalAccessException {
        boolean previousAccessible = field.isAccessible();
        field.setAccessible(true);
        fieldConsumer.doWith(field);
        field.setAccessible(previousAccessible);
    }
}

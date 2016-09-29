package org.licket.core.view.hippo.testing.ngclass;

import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.framework.hippo.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Predicates.assignableFrom;
import static com.google.common.base.Predicates.notNull;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
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

    public BlockBuilder decorate(BlockBuilder block) {
        doWithFields(angularClass.getClass(), field -> {
            doOnAccessible(field, accessibleField -> {
                ObjectLiteralBuilder objectProperty = (ObjectLiteralBuilder) accessibleField.get(angularClass);
                if (objectProperty == null) {
                    objectProperty = objectLiteral();
                }
                Name customName = accessibleField.getAnnotation(Name.class);
                PropertyNameBuilder assignmentProperty = property(thisLiteral(),
                        name(firstNonNull(trimToNull(customName.value()), accessibleField.getName())));


                block.appendStatement(expressionStatement(assignment()
                        .left(assignmentProperty)
                        .right(objectProperty)));
            });
        }, field -> {
            return notNull().apply(on(field).get()) && assignableFrom(ObjectLiteralBuilder.class).apply(field.getType());
        });
        return block;
    }

    private void doOnAccessible(Field field, ReflectionUtils.FieldCallback fieldConsumer)
            throws IllegalAccessException {
        boolean previousAccessible = field.isAccessible();
        field.setAccessible(true);
        fieldConsumer.doWith(field);
        field.setAccessible(previousAccessible);
    }
}

package org.licket.core.view.hippo.ngclass;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Predicates.assignableFrom;
import static com.google.common.base.Predicates.notNull;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.springframework.util.ReflectionUtils.doWithFields;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.licket.core.view.hippo.annotation.Name;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.springframework.util.ReflectionUtils;

/**
 * @author activey
 */
public class AngularClassPropertiesDecorator {

    private AngularClass angularClass;

    private AngularClassPropertiesDecorator(AngularClass angularClass) {
        this.angularClass = angularClass;
    }

    public static AngularClassPropertiesDecorator fromAngularClassProperties(AngularClass angularClass) {
        return new AngularClassPropertiesDecorator(angularClass);
    }

    public BlockBuilder decorate(BlockBuilder block) {
        doWithFields(angularClass.getClass(), field -> doOnAccessible(field, accessibleField -> {
            ObjectLiteralBuilder objectProperty = getObjectLiteral(field.getName());
            if (objectProperty == null) {
                objectProperty = objectLiteral();
            }
            Name customName = accessibleField.getAnnotation(Name.class);
            PropertyNameBuilder assignmentProperty = property(thisLiteral(),
                name(firstNonNull(trimToNull(customName.value()), accessibleField.getName())));

            block.appendStatement(expressionStatement(assignment().left(assignmentProperty).right(objectProperty)));
        }), field -> notNull().apply(on(field).get())
                && assignableFrom(ObjectLiteralBuilder.class).apply(field.getType()));
        return block;
    }

    private ObjectLiteralBuilder getObjectLiteral(String propertyName) throws IllegalAccessException {
        try {
            return (ObjectLiteralBuilder) getProperty(angularClass, propertyName);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void doOnAccessible(Field field, ReflectionUtils.FieldCallback fieldConsumer)
            throws IllegalAccessException {
        boolean previousAccessible = field.isAccessible();
        field.setAccessible(true);
        fieldConsumer.doWith(field);
        field.setAccessible(previousAccessible);
    }
}

package org.licket.core.view.hippo.vue.extend;

import org.licket.core.view.hippo.vue.annotation.Name;
import org.licket.framework.hippo.AbstractAstNodeBuilder;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Predicates.assignableFrom;
import static com.google.common.base.Predicates.notNull;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.joor.Reflect.on;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.VariableDeclarationBuilder.variableDeclaration;
import static org.licket.framework.hippo.VariableInitializerBuilder.variableInitializer;
import static org.springframework.util.ReflectionUtils.doWithFields;

/**
 * @author activey
 */
public class VuePropertiesDecorator {

  private VueClass vueClass;

  private VuePropertiesDecorator(VueClass vueClass) {
    this.vueClass = vueClass;
  }

  public static VuePropertiesDecorator fromVueClassProperties(VueClass vueClass) {
    return new VuePropertiesDecorator(vueClass);
  }

  public BlockBuilder decorate(BlockBuilder block) {
    doWithFields(vueClass.getClass(), field -> doOnAccessible(field, accessibleField -> {
      AbstractAstNodeBuilder<?> objectProperty = getPropertyAstBuilder(field.getName());
      if (objectProperty == null) {
        objectProperty = objectLiteral();
      }
      Name customName = accessibleField.getAnnotation(Name.class);
      block.appendStatement(expressionStatement(
              variableDeclaration().variable(
                      variableInitializer()
                              .target(name(firstNonNull(trimToNull(customName.value()), accessibleField.getName())))
                              .initializer(objectProperty)
              )
              )
      );
    }), field -> notNull().apply(on(field).get()) && isNodeBuilderField(field));
    return block;
  }

  private boolean isNodeBuilderField(Field field) {
    return assignableFrom(AbstractAstNodeBuilder.class).apply(field.getType());
  }

  private AbstractAstNodeBuilder<?> getPropertyAstBuilder(String propertyName) throws IllegalAccessException {
    try {
      // TODO do it some better way ...
      AbstractAstNodeBuilder<?> fieldValue = on(vueClass).field(propertyName).get();
      if (fieldValue != null) {
        return fieldValue;
      }
      return (AbstractAstNodeBuilder<?>) getProperty(vueClass, propertyName);
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

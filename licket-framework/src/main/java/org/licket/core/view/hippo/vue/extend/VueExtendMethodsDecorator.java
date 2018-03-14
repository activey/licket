package org.licket.core.view.hippo.vue.extend;

import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;

/**
 * @author activey
 */
public class VueExtendMethodsDecorator {

  private static final Logger LOGGER = LoggerFactory.getLogger(VueExtendMethodsDecorator.class);

  private VueClass vueClass;

  private VueExtendMethodsDecorator(VueClass vueClass) {
    this.vueClass = vueClass;
  }

  public static VueExtendMethodsDecorator fromClass(VueClass vueClass) {
    return new VueExtendMethodsDecorator(vueClass);
  }

  public ObjectLiteralBuilder decorate(ObjectLiteralBuilder objectLiteral) {
    stream(vueClass.getClass().getMethods()).forEach(method -> writeMemberFunctionBody(objectLiteral, method));
    return objectLiteral;
  }

  private void writeMemberFunctionBody(ObjectLiteralBuilder methodsObject, Method method) {
    Optional<VueComponentFunction> classFunction = getClassFunction(method);
    if (!classFunction.isPresent()) {
      return;
    }
    // declaring member function
    VueExtendMemberFunction memberFunction = new VueExtendMemberFunction();

    // appending extend method declaration
    methodsObject.objectProperty(
            propertyBuilder()
                    .name(firstNonNull(trimToNull(classFunction.get().value()), method.getName()))
                    .value(memberFunction.toFunctionNode(method, vueClass)));
  }

  private Optional<VueComponentFunction> getClassFunction(Method method) {
    if (isPrivate(method.getModifiers())) {
      LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
      return empty();
    }
    VueComponentFunction classFunction = method.getAnnotation(VueComponentFunction.class);
    if (classFunction == null) {
      return empty();
    }
    if (!stream(classFunction.predicates()).allMatch(vueClassPredicate -> vueClassPredicate.predicate().test(vueClass))) {
      return empty();
    }
    return ofNullable(classFunction);
  }
}

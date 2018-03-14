package org.licket.core.view.hippo.vue.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.licket.core.view.hippo.vue.annotation.VueComponentFunctionPredicate.ANY;

/**
 * @author activey
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
@SuppressWarnings("unused")
public @interface VueComponentFunction {

  String value() default "";

  VueComponentFunctionPredicate[] predicates() default {ANY};
}

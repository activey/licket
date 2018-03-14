package org.licket.core.view.hippo.vue.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author grabslu
 */
@Retention(RUNTIME)
@Documented
@SuppressWarnings("unused")
public @interface Name {

  String value() default "";
}

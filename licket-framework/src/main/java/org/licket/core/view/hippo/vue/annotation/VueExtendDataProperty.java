package org.licket.core.view.hippo.vue.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author activey
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface VueExtendDataProperty {

    String value() default "";
}

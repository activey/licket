package org.licket.core.view.hippo.vue.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author activey
 */
@Target(METHOD)
@Retention(RUNTIME)
@SuppressWarnings("unused")
public @interface OnVueMounted {
}

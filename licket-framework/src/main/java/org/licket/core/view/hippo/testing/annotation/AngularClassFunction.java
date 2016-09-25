package org.licket.core.view.hippo.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author activey
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface AngularClassFunction {

    String value() default "";
}

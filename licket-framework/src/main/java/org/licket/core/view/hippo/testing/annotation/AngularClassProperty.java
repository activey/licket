package org.licket.core.view.hippo.testing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author activey
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface AngularClassProperty {

    String value() default "";
}

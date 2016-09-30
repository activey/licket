package org.licket.core.view.hippo.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * @author grabslu
 */
@Retention(RUNTIME)
public @interface Name {

    String value() default "";
}

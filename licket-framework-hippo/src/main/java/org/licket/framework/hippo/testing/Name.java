package org.licket.framework.hippo.testing;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author grabslu
 */
@Retention(RUNTIME)
public @interface Name {

    String value();
}

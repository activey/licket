package org.licket.core.view.hippo.vue.annotation;

import org.licket.core.view.security.LicketMountPointAccess;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.licket.core.view.security.LicketMountPointAccess.PUBLIC;

/**
 * @author lukaszgrabski
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@SuppressWarnings("unused")
public @interface LicketMountPoint {

  String value();

  LicketMountPointAccess access() default PUBLIC;

  boolean samePathRouteEnter() default false;
}

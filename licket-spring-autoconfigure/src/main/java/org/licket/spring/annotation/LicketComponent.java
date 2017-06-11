package org.licket.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author activey
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Scope("session")
@Bean
public @interface LicketComponent {

    @AliasFor("value")
    String[] name() default {};

    @AliasFor("name")
    String[] value() default {};
}

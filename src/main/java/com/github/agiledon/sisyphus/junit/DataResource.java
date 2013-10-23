package com.github.agiledon.sisyphus.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataResource {
    String resourceName();
    String templateName() default "";
    Class<?> targetClass();
}

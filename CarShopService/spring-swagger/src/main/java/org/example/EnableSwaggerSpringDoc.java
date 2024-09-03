package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Turn on SpringDoc and swagger
 */
//Meta-annotation restricts the use of @EnableSwaggerSpringDoc to classes, interfaces, or enums.
//It cannot be applied to methods, fields, or other elements.
@Target(ElementType.TYPE)

//Meta-annotation specifies that @EnableSwaggerSpringDoc will be available at runtime.
//it allows Spring to detect and process this annotation during the execution of the application.
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableSwaggerSpringDoc {
}

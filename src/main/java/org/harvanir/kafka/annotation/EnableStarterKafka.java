package org.harvanir.kafka.annotation;

import org.harvanir.kafka.registry.PlatformRegistrar;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author Harvan Irsyadi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PlatformRegistrar.class)
public @interface EnableStarterKafka {

}
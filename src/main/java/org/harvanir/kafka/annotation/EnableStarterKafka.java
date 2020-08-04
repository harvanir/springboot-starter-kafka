package org.harvanir.kafka.annotation;

import org.harvanir.kafka.registry.PlatformRegistrar;
import org.harvanir.kafka.registry.container.KafkaConsumerContainerMapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author Harvan Irsyadi */
@EnableConfigurationProperties(KafkaConsumerContainerMapProperties.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PlatformRegistrar.class)
public @interface EnableStarterKafka {}

package org.harvanir.kafka.registry;

import org.harvanir.kafka.registry.container.KafkaConsumerContainerRegistrar;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/** @author Harvan Irsyadi */
public class PlatformRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

  private Environment environment;

  @Override
  public void setEnvironment(@NonNull Environment environment) {
    this.environment = environment;
  }

  @Override
  public void registerBeanDefinitions(
      @NonNull AnnotationMetadata annotationMetadata,
      @NonNull BeanDefinitionRegistry beanDefinitionRegistry) {
    registerConsumerContainer(beanDefinitionRegistry);
  }

  private void registerConsumerContainer(BeanDefinitionRegistry registry) {
    new KafkaConsumerContainerRegistrar().register(registry, environment);
  }
}

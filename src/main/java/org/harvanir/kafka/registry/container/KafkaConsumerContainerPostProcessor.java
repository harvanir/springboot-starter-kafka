package org.harvanir.kafka.registry.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.lang.NonNull;

/** @author Harvan Irsyadi */
@Slf4j
public class KafkaConsumerContainerPostProcessor implements BeanPostProcessor {

  private final KafkaConsumerContainerMapProperties kafkaConsumerContainerMapProperties;

  public KafkaConsumerContainerPostProcessor(
      KafkaConsumerContainerMapProperties kafkaConsumerContainerMapProperties) {
    this.kafkaConsumerContainerMapProperties = kafkaConsumerContainerMapProperties;
  }

  @Override
  public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) {
    KafkaConsumerContainerProperties kafkaConsumerContainerProperties;

    if (bean instanceof ConcurrentKafkaListenerContainerFactory
        && (kafkaConsumerContainerProperties =
                kafkaConsumerContainerMapProperties.getConsumersContainer().get(beanName))
            != null) {
      log.info("Setup additional properties for bean: {}", beanName);

      ((ConcurrentKafkaListenerContainerFactory<?, ?>) bean)
          .getContainerProperties()
          .setAckMode(kafkaConsumerContainerProperties.getAckMode());
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) {
    return bean;
  }
}

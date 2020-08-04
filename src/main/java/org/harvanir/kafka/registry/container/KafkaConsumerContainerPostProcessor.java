package org.harvanir.kafka.registry.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/** @author Harvan Irsyadi */
@Slf4j
public class KafkaConsumerContainerPostProcessor implements BeanPostProcessor {

  private final KafkaConsumerContainerMapProperties kafkaConsumerContainerMapProperties;

  public KafkaConsumerContainerPostProcessor(
      KafkaConsumerContainerMapProperties kafkaConsumerContainerMapProperties) {
    this.kafkaConsumerContainerMapProperties = kafkaConsumerContainerMapProperties;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
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
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    return bean;
  }
}

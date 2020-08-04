package org.harvanir.kafka.autoconfigure;

import org.harvanir.kafka.annotation.EnableStarterKafka;
import org.harvanir.kafka.registry.container.KafkaConsumerContainerMapProperties;
import org.harvanir.kafka.registry.container.KafkaConsumerContainerPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Harvan Irsyadi */
@EnableStarterKafka
@Configuration(proxyBeanMethods = false)
public class StarterKafkaAutoConfiguration {

  @Bean
  public KafkaConsumerContainerPostProcessor kafkaConsumerContainerPostProcessor(
      KafkaConsumerContainerMapProperties kafkaConsumerContainerMapProperties) {
    return new KafkaConsumerContainerPostProcessor(kafkaConsumerContainerMapProperties);
  }
}

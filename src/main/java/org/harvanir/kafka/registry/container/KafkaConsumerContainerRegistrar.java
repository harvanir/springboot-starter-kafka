package org.harvanir.kafka.registry.container;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/** @author Harvan Irsyadi */
@Slf4j
public class KafkaConsumerContainerRegistrar {

  public void register(BeanDefinitionRegistry beanDefinitionRegistry, Environment environment) {
    registerFactory(constructPropertiesMap(environment), beanDefinitionRegistry);
  }

  private KafkaConsumerContainerMapProperties constructPropertiesMap(Environment environment) {
    ConfigurationProperties configurationProperties =
        KafkaConsumerContainerMapProperties.class.getAnnotation(ConfigurationProperties.class);
    KafkaConsumerContainerMapProperties propertiesMap =
        Binder.get(environment)
            .bind(configurationProperties.prefix(), KafkaConsumerContainerMapProperties.class)
            .get();

    propertiesMap
        .getConsumersContainer()
        .forEach(
            (key, schedulerProperties) ->
                log.info(
                    "Loading kafka scheduler: key: \"{}\", schedulerProperties: {}",
                    key,
                    schedulerProperties));

    return propertiesMap;
  }

  private Map<String, Object> consumerConfigs(KafkaConsumerContainerProperties properties) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getAutoOffsetReset());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());

    return props;
  }

  private ConsumerFactory<String, String> consumerFactory(
      KafkaConsumerContainerProperties properties) {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(properties));
  }

  private void registerDefault(
      KafkaConsumerContainerMapProperties propertiesFactory, BeanDefinitionRegistry registry) {
    KafkaConsumerContainerProperties defaultProperties =
        propertiesFactory.getConsumersContainer().get("default");

    if (defaultProperties != null) {
      registerBeanDefinition(registry, "kafkaListenerContainerFactory", defaultProperties);
    }
  }

  private GenericBeanDefinition createBeanDefinition(KafkaConsumerContainerProperties properties) {
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(ConcurrentKafkaListenerContainerFactory.class);

    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();

    propertyValues.addPropertyValue("concurrency", properties.getConcurrency());
    propertyValues.addPropertyValue("consumerFactory", consumerFactory(properties));

    return beanDefinition;
  }

  private void registerBeanDefinition(
      BeanDefinitionRegistry registry,
      String beanName,
      KafkaConsumerContainerProperties properties) {
    log.info("Registering bean \"{}\" with properties: {}", beanName, properties);

    GenericBeanDefinition beanDefinition = createBeanDefinition(properties);
    registry.registerBeanDefinition(beanName, beanDefinition);
  }

  private void registerFactory(
      KafkaConsumerContainerMapProperties propertiesMap, BeanDefinitionRegistry registry) {
    registerDefault(propertiesMap, registry);

    propertiesMap
        .getConsumersContainer()
        .forEach((key, properties) -> registerBeanDefinition(registry, key, properties));
  }
}

package org.harvanir.kafka.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlatformRegistrarTest {

  @SpringBootApplication
  static class Main {}

  @Autowired private ApplicationContext applicationContext;

  @Test
  public void testLoadContext() throws NoSuchFieldException, IllegalAccessException {
    Object beanDefault = applicationContext.getBean("default");
    Object beanHighConcurrent = applicationContext.getBean("high-concurrent");

    log.info("default: {}", beanDefault);
    log.info("high-concurrent: {}", beanHighConcurrent);

    assertNotNull(applicationContext);
    assertNotNull(beanDefault);
    assertNotNull(beanHighConcurrent);
    assertTrue(beanDefault instanceof ConcurrentKafkaListenerContainerFactory);
    assertTrue(beanHighConcurrent instanceof ConcurrentKafkaListenerContainerFactory);

    ConcurrentKafkaListenerContainerFactory<?, ?> container =
        (ConcurrentKafkaListenerContainerFactory<?, ?>) beanDefault;
    assertContainer(container, "localhost:9092", "groupId", 2, "latest", AckMode.MANUAL_IMMEDIATE);

    container = (ConcurrentKafkaListenerContainerFactory<?, ?>) beanHighConcurrent;
    assertContainer(container, "localhost:9093", "groupId2", 200, "none", AckMode.MANUAL);
  }

  private void assertContainer(
      ConcurrentKafkaListenerContainerFactory<?, ?> container,
      String bootstrapServers,
      String groupId,
      Integer concurrency,
      String autoOffsetReset,
      AckMode ackMode)
      throws NoSuchFieldException, IllegalAccessException {
    DefaultKafkaConsumerFactory<?, ?> factory =
        (DefaultKafkaConsumerFactory<?, ?>) container.getConsumerFactory();

    Assert.assertEquals(
        bootstrapServers,
        factory.getConfigurationProperties().get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    Assert.assertEquals(
        groupId, factory.getConfigurationProperties().get(ConsumerConfig.GROUP_ID_CONFIG));
    Assert.assertEquals(
        autoOffsetReset,
        factory.getConfigurationProperties().get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
    Assert.assertEquals(ackMode, container.getContainerProperties().getAckMode());

    Field concurrencyField =
        ConcurrentKafkaListenerContainerFactory.class.getDeclaredField("concurrency");
    concurrencyField.setAccessible(true);

    Assert.assertEquals(concurrency, concurrencyField.get(container));
  }
}

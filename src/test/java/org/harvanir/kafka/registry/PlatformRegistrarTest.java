package org.harvanir.kafka.registry;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlatformRegistrarTest {

  @SpringBootApplication
  static class Main {

  }

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void testLoadContext() {
    Object beanDefault = applicationContext.getBean("default");
    Object beanHighConcurrent = applicationContext.getBean("high-concurrent");

    log.info("default: {}", beanDefault);
    log.info("high-concurrent: {}", beanHighConcurrent);

    assertNotNull(applicationContext);
    assertNotNull(beanDefault);
    assertNotNull(beanHighConcurrent);
    assertTrue(beanDefault instanceof ConcurrentKafkaListenerContainerFactory);
    assertTrue(beanHighConcurrent instanceof ConcurrentKafkaListenerContainerFactory);
  }
}
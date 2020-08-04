package org.harvanir.kafka.registry.container;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KafkaConsumerContainerMapPropertiesTest {

  @Test
  public void testProperty() {
    KafkaConsumerContainerMapProperties map = new KafkaConsumerContainerMapProperties();

    assertThat(map, Matchers.hasProperty("consumersContainer"));
  }

  @Test
  public void testDefaultValue() {
    KafkaConsumerContainerMapProperties map = new KafkaConsumerContainerMapProperties();

    assertNotNull(map.getConsumersContainer());
    assertTrue(map.getConsumersContainer().isEmpty());
  }

  @Test
  public void testGetterSetter() {
    String key = "default";

    KafkaConsumerContainerMapProperties containerPropertiesMap =
        new KafkaConsumerContainerMapProperties();
    Map<String, KafkaConsumerContainerProperties> map = new HashMap<>();
    KafkaConsumerContainerProperties properties = new KafkaConsumerContainerProperties();

    map.put(key, properties);
    containerPropertiesMap.setConsumersContainer(map);

    assertFalse(containerPropertiesMap.getConsumersContainer().isEmpty());
    assertEquals(map, containerPropertiesMap.getConsumersContainer());
  }
}

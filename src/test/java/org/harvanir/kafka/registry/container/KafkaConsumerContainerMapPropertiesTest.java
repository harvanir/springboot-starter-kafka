package org.harvanir.kafka.registry.container;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class KafkaConsumerContainerMapPropertiesTest {

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

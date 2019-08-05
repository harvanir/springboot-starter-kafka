package org.harvanir.kafka.registry.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Test;

public class KafkaConsumerContainerPropertiesMapTest {

  @Test
  public void testProperty() {
    KafkaConsumerContainerPropertiesMap map = new KafkaConsumerContainerPropertiesMap();

    assertThat(map, Matchers.hasProperty("consumersContainer"));
  }

  @Test
  public void testDefaultValue() {
    KafkaConsumerContainerPropertiesMap map = new KafkaConsumerContainerPropertiesMap();

    assertNotNull(map.getConsumersContainer());
    assertTrue(map.getConsumersContainer().isEmpty());
  }

  @Test
  public void testGetterSetter() {
    String key = "default";

    KafkaConsumerContainerPropertiesMap containerPropertiesMap = new KafkaConsumerContainerPropertiesMap();
    Map<String, KafkaConsumerContainerProperties> map = new HashMap<>();
    KafkaConsumerContainerProperties properties = new KafkaConsumerContainerProperties();

    map.put(key, properties);
    containerPropertiesMap.setConsumersContainer(map);

    assertFalse(containerPropertiesMap.getConsumersContainer().isEmpty());
    assertEquals(map, containerPropertiesMap.getConsumersContainer());
  }
}
package org.harvanir.kafka.registry.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;

public class KafkaConsumerContainerPropertiesTest {

  @Test
  public void testProperty() {
    KafkaConsumerContainerProperties properties = new KafkaConsumerContainerProperties();

    assertThat(properties, Matchers.hasProperty("bootstrapServers"));
    assertThat(properties, Matchers.hasProperty("groupId"));
    assertThat(properties, Matchers.hasProperty("concurrency"));
    assertThat(properties, Matchers.hasProperty("ackMode"));
    assertThat(properties, Matchers.hasProperty("autoOffsetReset"));
  }

  @Test
  public void testDefaultValue() {
    KafkaConsumerContainerProperties properties = new KafkaConsumerContainerProperties();

    assertNull(properties.getBootstrapServers());
    assertNull(properties.getGroupId());
    assertEquals(100, properties.getConcurrency());
    assertEquals(AckMode.BATCH, properties.getAckMode());
    assertEquals("latest", properties.getAutoOffsetReset());
    assertNotNull(properties.toString());
  }

  @Test
  public void testGetterSetter() {
    String bootstrapServers = "localhost:9092";
    String groupId = "groupId";
    int concurrency = 10;
    AckMode ackMode = AckMode.MANUAL;
    String autoOffsetReset = "earlier";

    KafkaConsumerContainerProperties properties = new KafkaConsumerContainerProperties();

    properties.setBootstrapServers(bootstrapServers);
    properties.setGroupId(groupId);
    properties.setConcurrency(concurrency);
    properties.setAckMode(ackMode);
    properties.setAutoOffsetReset(autoOffsetReset);

    assertEquals(bootstrapServers, properties.getBootstrapServers());
    assertEquals(groupId, properties.getGroupId());
    assertEquals(concurrency, properties.getConcurrency());
    assertEquals(ackMode, properties.getAckMode());
    assertEquals(autoOffsetReset, properties.getAutoOffsetReset());
  }
}
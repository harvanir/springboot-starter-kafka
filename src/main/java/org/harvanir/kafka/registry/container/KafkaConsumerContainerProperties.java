package org.harvanir.kafka.registry.container;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;

/**
 * @author Harvan Irsyadi
 */
@ToString
@Getter
@Setter
public class KafkaConsumerContainerProperties {

  private String bootstrapServers;

  private String groupId;

  private int concurrency = 100;

  private AckMode ackMode = AckMode.BATCH;

  private String autoOffsetReset = "latest";
}
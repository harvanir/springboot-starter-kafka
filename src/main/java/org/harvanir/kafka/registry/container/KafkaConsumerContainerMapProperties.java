package org.harvanir.kafka.registry.container;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/** @author Harvan Irsyadi */
@ConfigurationProperties(prefix = "message-broker.kafka")
@Getter
@Setter
public class KafkaConsumerContainerMapProperties {

  private Map<String, KafkaConsumerContainerProperties> consumersContainer = new HashMap<>();
}

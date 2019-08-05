package org.harvanir.kafka.registry.container;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Harvan Irsyadi
 */
@Getter
@Setter
class KafkaConsumerContainerPropertiesMap {

  static final String PROPERTIES_PREFIX = "message-broker.kafka";

  private Map<String, KafkaConsumerContainerProperties> consumersContainer = new HashMap<>();
}
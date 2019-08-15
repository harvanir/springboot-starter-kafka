package org.harvanir.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author Harvan Irsyadi
 */
@Slf4j
@SpringBootApplication
public class Main {

  @KafkaListener(topicPattern = "test", containerFactory = "high-concurrent")
  public void listen(String message) {
    log.info("Message: {}", message);
  }


  public static void main(String args[]) {
    SpringApplication.run(Main.class, args);
  }
}
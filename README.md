# Getting Started

## Objective
<b>springboot-starter-kafka</b> will create consumer container factory bean with 
customizable concurrency and other specification (org.apache.kafka.clients.consumer.ConsumerConfig) based on the declared properties.
<br/>
This <b>springboot-starter-kafka</b> support for annotation <code>@KafkaListener</code>.<br/>

## Background
By default, spring boot will create some beans as default consumer container factory:
```java
@Configuration
@ConditionalOnClass(EnableKafka.class)
class KafkaAnnotationDrivenConfiguration {

	private final KafkaProperties properties;

	KafkaAnnotationDrivenConfiguration(KafkaProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer() {
		ConcurrentKafkaListenerContainerFactoryConfigurer configurer = new ConcurrentKafkaListenerContainerFactoryConfigurer();
		configurer.setKafkaProperties(this.properties);
		return configurer;
	}

	@Bean
	@ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, kafkaConsumerFactory);
		return factory;
	}

	@Configuration
	@EnableKafka
	@ConditionalOnMissingBean(
			name = KafkaListenerConfigUtils.KAFKA_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)
	protected static class EnableKafkaConfiguration {

	}
}
```
This is good for application that only need one behaviour/specification of kafka consumer.<br/><br/>
When you need another consumer factory (bean=<b>"high-concurrent"</b>) you have to define the code bellow for each consumer factory specification:
```java
import org.apache.kafka.clients.consumer.ConsumerConfig;

@Configuration
@ConfigurationProperties(prefix = "message-broker.kafka.high-concurrent")
class KafkaConfiguration {
  
  private String bootstrapServers;
  
  private String groupId;

  private int concurrency;
  
  private Map<String, Object> highConcurrentProperties() {
    Map<String, Object> props = new HashMap<>();
    
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getAutoOffsetReset());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
    
    return props;
  }
  
  private ConsumerFactory<String, String> highConcurrentConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(highConcurrentProperties());
  }
  
  @Bean
  @ConditionalOnMissingBean(name = "high-concurrent")
  public ConcurrentKafkaListenerContainerFactory<?, ?> highConcurrent() {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
    factory.setConsumerFactory(highConcurrentConsumerFactory());
    return factory; 
  }
}
```   

## Solution
The <b>springboot-starter-kafka</b> use "spring.factories" to enable auto load on spring boot context.<br/>
To use this library, import this dependency and it will auto read & create the beans needed.<br/><br/> 
Here is some samples of uses:


##### 1. Similar uses of default containerFactory 'kafkaListenerContainerFactory'
yml:
```yaml
message-broker:
  kafka:
    consumers-container:
      default:
        bootstrap-servers: localhost:9092
        group-id: groupId
        concurrency: 2
        auto-offset-reset: latest
```
java:
```java
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  /**
   * See {@link org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration#kafkaListenerContainerFactory}
   */
  static class Listener {

    @KafkaListener(topics = "test")
    public void listen1(String message) {
    }

    @KafkaListener(topics = "test2", containerFactory = "kafkaListenerContainerFactory")
    public void listen2(String message) {
    }

    @KafkaListener(topics = "test3", containerFactory = "default")
    public void listen3(String message) {
    }
  }
}
```
<br/>

##### 2. Using another containerFactory
```yaml
message-broker:
  kafka:
    consumers-container:
      default:
        bootstrap-servers: localhost:9092
        group-id: groupId
        concurrency: 2
        auto-offset-reset: latest
      high-concurrent:
        bootstrap-servers: localhost:9092
        group-id: groupId
        concurrency: 200
        auto-offset-reset: latest
```
java:
```java
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  static class Listener {

    @KafkaListener(topics = "test", containerFactory = "high-concurrent")
    public void listen(String message) {
    }
  }
}
```
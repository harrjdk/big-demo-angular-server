package dev.hornetshell.bigdemoangular.configuration;

import dev.hornetshell.bigdemoangular.types.SmartDataRecord;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/** Kafka configuration. */
@Profile("kafka")
@Configuration
@EnableKafka
public class KafkaConfig {
  private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

  public static final String EARLIEST = "earliest";
  public static final String BOOTSTRAP_SERVERS = "bootstrap-servers";
  private final Environment env;

  /**
   * Simple Constructor.
   *
   * @param env the Environment provided by spring.
   */
  @Autowired
  public KafkaConfig(Environment env) {
    this.env = env;
  }

  /**
   * The consumer configuration.
   *
   * <p>For the sake of a generic solution, we'll treat everything as strings.
   *
   * @return the consumer config.
   */
  @Bean
  public Map<String, Object> consumerConfig() {
    final String bootstrapServers =
        env.getProperty(BOOTSTRAP_SERVERS, "PLAINTEXT://localhost:9092");
    log.info("Using {} as bootstrap servers...", bootstrapServers);
    final Map<String, Object> props = new HashMap<>();
    if (!bootstrapServers.isBlank()) {
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    }
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, SmartDataRecord.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);

    return props;
  }

  /**
   * The consumer factory.
   *
   * @return the consumer factory.
   */
  @Bean
  public ConsumerFactory<String, SmartDataRecord> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfig());
  }

  /**
   * The listener container factory.
   *
   * @return the listener container factory.
   */
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SmartDataRecord>>
      kafkaListenerContainerFactory() {
    final ConcurrentKafkaListenerContainerFactory<String, SmartDataRecord> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}

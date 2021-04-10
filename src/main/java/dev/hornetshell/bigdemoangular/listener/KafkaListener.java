package dev.hornetshell.bigdemoangular.listener;

import dev.hornetshell.bigdemoangular.services.SmartDataService;
import dev.hornetshell.bigdemoangular.types.SmartDataRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Listener for the Kafka stream. */
@Profile("kafka")
@Component
public class KafkaListener {
  private static final Logger log = LoggerFactory.getLogger(KafkaListener.class);

  public static final String TOPIC = "backblaze_smart";
  public static final String GROUP_ID = "big-demo-listener";

  private final SmartDataService smartDataService;

  @Autowired
  public KafkaListener(SmartDataService smartDataService) {
    this.smartDataService = smartDataService;
  }

  /**
   * Listener for messages.
   *
   * <p>This listens to smart data from our sample kafka topic.
   *
   * @param consumerRecord The record from the topic.
   */
  @org.springframework.kafka.annotation.KafkaListener(groupId = GROUP_ID, topics = TOPIC)
  public void listen(ConsumerRecord<String, SmartDataRecord> consumerRecord) {
    log.trace("Received record={}", consumerRecord);

    final SmartDataRecord smartDataRecord = consumerRecord.value();
    log.debug("Received smartDataRecord={}", smartDataRecord);

    smartDataService.saveSmartData(smartDataRecord);
  }
}

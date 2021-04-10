package dev.hornetshell.bigdemoangular.listener;

import dev.hornetshell.bigdemoangular.services.SmartDataService;
import dev.hornetshell.bigdemoangular.types.SmartDataRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("kafka")
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@EnableAutoConfiguration(
    exclude = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
    })
class KafkaListenerTest {
  public static final String KEY =
      "{\n" + "      \"serial_number\": \"MJ0351YNG9Z0XA\"\n" + "    }";
  public static final String VALUE =
      "{\n"
          + "      \"date\": \"2017-01-01\",\n"
          + "      \"serial_number\": \"MJ0351YNG9Z0XA\",\n"
          + "      \"model\": \"Hitachi HDS5C3030ALA630\",\n"
          + "      \"capacity_bytes\": 3000592982016,\n"
          + "      \"failure\": 0,\n"
          + "      \"smart_1_normalized\": 100,\n"
          + "      \"smart_1_raw\": 0,\n"
          + "      \"smart_2_normalized\": 135,\n"
          + "      \"smart_2_raw\": 108,\n"
          + "      \"smart_3_normalized\": 127,\n"
          + "      \"smart_3_raw\": 554,\n"
          + "      \"smart_4_normalized\": 100,\n"
          + "      \"smart_4_raw\": 15,\n"
          + "      \"smart_5_normalized\": 100,\n"
          + "      \"smart_5_raw\": 0,\n"
          + "      \"smart_7_normalized\": 100,\n"
          + "      \"smart_7_raw\": 0,\n"
          + "      \"smart_8_normalized\": 122,\n"
          + "      \"smart_8_raw\": 37,\n"
          + "      \"smart_9_normalized\": 95,\n"
          + "      \"smart_9_raw\": 36714,\n"
          + "      \"smart_10_normalized\": 100,\n"
          + "      \"smart_10_raw\": 0,\n"
          + "      \"smart_11_normalized\": null,\n"
          + "      \"smart_11_raw\": null,\n"
          + "      \"smart_12_normalized\": 100,\n"
          + "      \"smart_12_raw\": 15,\n"
          + "      \"smart_13_normalized\": null,\n"
          + "      \"smart_13_raw\": null,\n"
          + "      \"smart_15_normalized\": null,\n"
          + "      \"smart_15_raw\": null,\n"
          + "      \"smart_22_normalized\": null,\n"
          + "      \"smart_22_raw\": null,\n"
          + "      \"smart_183_normalized\": null,\n"
          + "      \"smart_183_raw\": null,\n"
          + "      \"smart_184_normalized\": null,\n"
          + "      \"smart_184_raw\": null,\n"
          + "      \"smart_187_normalized\": null,\n"
          + "      \"smart_187_raw\": null,\n"
          + "      \"smart_188_normalized\": null,\n"
          + "      \"smart_188_raw\": null,\n"
          + "      \"smart_189_normalized\": null,\n"
          + "      \"smart_189_raw\": null,\n"
          + "      \"smart_190_normalized\": null,\n"
          + "      \"smart_190_raw\": null,\n"
          + "      \"smart_191_normalized\": null,\n"
          + "      \"smart_191_raw\": null,\n"
          + "      \"smart_192_normalized\": 100,\n"
          + "      \"smart_192_raw\": 410,\n"
          + "      \"smart_193_normalized\": 100,\n"
          + "      \"smart_193_raw\": 410,\n"
          + "      \"smart_194_normalized\": 214,\n"
          + "      \"smart_194_raw\": 28,\n"
          + "      \"smart_195_normalized\": null,\n"
          + "      \"smart_195_raw\": null,\n"
          + "      \"smart_196_normalized\": 100,\n"
          + "      \"smart_196_raw\": 0,\n"
          + "      \"smart_197_normalized\": 100,\n"
          + "      \"smart_197_raw\": 0,\n"
          + "      \"smart_198_normalized\": 100,\n"
          + "      \"smart_198_raw\": 0,\n"
          + "      \"smart_199_normalized\": 200,\n"
          + "      \"smart_199_raw\": 0,\n"
          + "      \"smart_200_normalized\": null,\n"
          + "      \"smart_200_raw\": null,\n"
          + "      \"smart_201_normalized\": null,\n"
          + "      \"smart_201_raw\": null,\n"
          + "      \"smart_220_normalized\": null,\n"
          + "      \"smart_220_raw\": null,\n"
          + "      \"smart_222_normalized\": null,\n"
          + "      \"smart_222_raw\": null,\n"
          + "      \"smart_223_normalized\": null,\n"
          + "      \"smart_223_raw\": null,\n"
          + "      \"smart_224_normalized\": null,\n"
          + "      \"smart_224_raw\": null,\n"
          + "      \"smart_225_normalized\": null,\n"
          + "      \"smart_225_raw\": null,\n"
          + "      \"smart_226_normalized\": null,\n"
          + "      \"smart_226_raw\": null,\n"
          + "      \"smart_240_normalized\": null,\n"
          + "      \"smart_240_raw\": null,\n"
          + "      \"smart_241_normalized\": null,\n"
          + "      \"smart_241_raw\": null,\n"
          + "      \"smart_242_normalized\": null,\n"
          + "      \"smart_242_raw\": null,\n"
          + "      \"smart_250_normalized\": null,\n"
          + "      \"smart_250_raw\": null,\n"
          + "      \"smart_251_normalized\": null,\n"
          + "      \"smart_251_raw\": null,\n"
          + "      \"smart_252_normalized\": null,\n"
          + "      \"smart_252_raw\": null,\n"
          + "      \"smart_254_normalized\": null,\n"
          + "      \"smart_254_raw\": null,\n"
          + "      \"smart_255_normalized\": null,\n"
          + "      \"smart_255_raw\": null\n"
          + "    }";

  // TODO: make this a method if we need more options in the future
  public static SmartDataRecord smartDataRecord = new SmartDataRecord();

  static {
    smartDataRecord.setModel("Hitachi HDS5C3030ALA630");
    smartDataRecord.setSerialNumber("MJ0351YNG9Z0XA");
    smartDataRecord.setCapacityBytes(3000592982016L);
  }

  @MockBean private SmartDataService smartDataService;

  @Autowired private KafkaListener subject;

  @Autowired KafkaTemplate<String, String> kafkaTemplate;

  @Test
  void testCanConsumeMessage() throws InterruptedException {
    final ProducerRecord<String, String> producerRecord =
        new ProducerRecord<String, String>(KafkaListener.TOPIC, KEY, VALUE);
    kafkaTemplate.send(producerRecord);
    // sleep for 5 seconds to give the app time to read
    Thread.sleep(5000L);
    Mockito.verify(smartDataService).saveSmartData(smartDataRecord);
  }
}

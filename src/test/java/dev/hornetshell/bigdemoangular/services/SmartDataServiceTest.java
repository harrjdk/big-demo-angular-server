package dev.hornetshell.bigdemoangular.services;

import dev.hornetshell.bigdemoangular.repositories.SmartDataRepository;
import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import dev.hornetshell.bigdemoangular.types.SmartDataRecord;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(
    exclude = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class
    })
class SmartDataServiceTest {

  @MockBean private SmartDataRepository smartDataRepository;

  @Autowired private SmartDataService subject;

  @Test
  void verifySaves() {
    final SmartDataRecord smartDataRecord = new SmartDataRecord();
    smartDataRecord.setCapacityBytes(100L);
    smartDataRecord.setSerialNumber("testserial");
    smartDataRecord.setModel("testmodel");

    final SmartData smartData = new SmartData();
    smartData.setCapacityBytes(100L);
    smartData.setSerialNumber("testserial");
    smartData.setModel("testmodel");

    subject.saveSmartData(smartDataRecord);
    Mockito.verify(smartDataRepository).save(smartData);
  }

  @Test
  void verifyGetRecent() {
    final SmartData smartData = new SmartData();
    smartData.setId(1L);
    smartData.setCapacityBytes(100L);
    smartData.setSerialNumber("testserial");
    smartData.setModel("testmodel");

    Mockito.when(smartDataRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(smartData));
    final Optional<SmartData> result = subject.getRecentSmartData();
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(smartData, result.get());
  }
}

package dev.hornetshell.bigdemoangular.repositories;

import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(
    properties = {
      "spring.datasource.hikari.schema=public",
      "spring.liquibase.default-schema=public"
    })
@ActiveProfiles({"jpa"})
class SmartDataRepositoryTest {

  @Autowired private SmartDataRepository subject;

  @Test
  void canGetTopEntries() {
    Assertions.assertTrue(subject.findTopByOrderByIdDesc().isEmpty());
    final SmartData smartData = new SmartData();
    smartData.setSerialNumber("testserial");
    smartData.setModel("testmodel");
    smartData.setCapacityBytes(1234567890L);
    subject.save(smartData);

    final Optional<SmartData> firstResult = subject.findTopByOrderByIdDesc();
    Assertions.assertTrue(firstResult.isPresent());
    Assertions.assertEquals(smartData.getCapacityBytes(), firstResult.get().getCapacityBytes());
    Assertions.assertEquals(smartData.getModel(), firstResult.get().getModel());
    Assertions.assertEquals(smartData.getSerialNumber(), firstResult.get().getSerialNumber());

    final Optional<SmartData> secondResult = subject.findTopByOrderByIdDesc();
    Assertions.assertTrue(secondResult.isPresent());
    Assertions.assertEquals(firstResult.get(), secondResult.get());

    final SmartData smartData2 = new SmartData();
    smartData2.setSerialNumber("testserial2");
    smartData2.setModel("testmodel2");
    smartData2.setCapacityBytes(9876543210L);
    subject.save(smartData2);

    final Optional<SmartData> thirdResult = subject.findTopByOrderByIdDesc();
    Assertions.assertTrue(thirdResult.isPresent());
    Assertions.assertEquals(smartData2.getCapacityBytes(), thirdResult.get().getCapacityBytes());
    Assertions.assertEquals(smartData2.getModel(), thirdResult.get().getModel());
    Assertions.assertEquals(smartData2.getSerialNumber(), thirdResult.get().getSerialNumber());
    Assertions.assertTrue(thirdResult.get().getId() > secondResult.get().getId());
  }
}

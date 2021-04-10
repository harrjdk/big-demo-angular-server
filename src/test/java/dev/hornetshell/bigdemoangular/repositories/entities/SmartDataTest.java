package dev.hornetshell.bigdemoangular.repositories.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SmartDataTest {

  @Test
  void verifyEquality() {
    final SmartData smartData1 = new SmartData();
    smartData1.setId(1L);
    smartData1.setCapacityBytes(100L);
    smartData1.setModel("test");
    smartData1.setSerialNumber("test123");

    final SmartData smartData2 = new SmartData();
    smartData2.setId(1L);
    smartData2.setCapacityBytes(100L);
    smartData2.setModel("test");
    smartData2.setSerialNumber("test123");

    Assertions.assertEquals(smartData1, smartData2);

    smartData2.setSerialNumber("foo");
    Assertions.assertNotEquals(smartData1, smartData2);
  }

  @Test
  void verifyHash() {
    final SmartData smartData1 = new SmartData();
    smartData1.setId(1L);
    smartData1.setCapacityBytes(100L);
    smartData1.setModel("test");
    smartData1.setSerialNumber("test123");

    final SmartData smartData2 = new SmartData();
    smartData2.setId(1L);
    smartData2.setCapacityBytes(100L);
    smartData2.setModel("test");
    smartData2.setSerialNumber("test123");

    Assertions.assertEquals(smartData1.hashCode(), smartData2.hashCode());

    smartData2.setSerialNumber("foo");
    Assertions.assertNotEquals(smartData1.hashCode(), smartData2.hashCode());
  }
}

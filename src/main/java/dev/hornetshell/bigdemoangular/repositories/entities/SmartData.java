package dev.hornetshell.bigdemoangular.repositories.entities;

import java.util.Objects;
import javax.persistence.*;

/** SmartData record entity. */
@Entity
@Table(name = "smartdata")
public class SmartData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "model")
  private String model;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "capacity_bytes")
  private long capacityBytes;

  public SmartData() {
    // default constructor
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public long getCapacityBytes() {
    return capacityBytes;
  }

  public void setCapacityBytes(long capacityBytes) {
    this.capacityBytes = capacityBytes;
  }

  /* Generated code */

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SmartData smartData = (SmartData) o;
    return getCapacityBytes() == smartData.getCapacityBytes()
        && Objects.equals(getId(), smartData.getId())
        && Objects.equals(getModel(), smartData.getModel())
        && Objects.equals(getSerialNumber(), smartData.getSerialNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getModel(), getSerialNumber(), getCapacityBytes());
  }
}

package dev.hornetshell.bigdemoangular.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

/** SmartDataRecord */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"serial_number", "model", "capacity_bytes"})
public class SmartDataRecord {
  /** The serial number of the drive (Required) */
  @JsonProperty("serial_number")
  @JsonPropertyDescription("The serial number of the drive")
  private String serialNumber;
  /** The model of the drive (Required) */
  @JsonProperty("model")
  @JsonPropertyDescription("The model of the drive")
  private String model;
  /** The drive's total capacity (Required) */
  @JsonProperty("capacity_bytes")
  @JsonPropertyDescription("The drive's total capacity")
  private Long capacityBytes;

  /** The serial number of the drive (Required) */
  @JsonProperty("serial_number")
  public String getSerialNumber() {
    return serialNumber;
  }

  /** The serial number of the drive (Required) */
  @JsonProperty("serial_number")
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /** The model of the drive (Required) */
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  /** The model of the drive (Required) */
  @JsonProperty("model")
  public void setModel(String model) {
    this.model = model;
  }

  /** The drive's total capacity (Required) */
  @JsonProperty("capacity_bytes")
  public Long getCapacityBytes() {
    return capacityBytes;
  }

  /** The drive's total capacity (Required) */
  @JsonProperty("capacity_bytes")
  public void setCapacityBytes(Long capacityBytes) {
    this.capacityBytes = capacityBytes;
  }

  /* Generated code */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SmartDataRecord that = (SmartDataRecord) o;
    return Objects.equals(getSerialNumber(), that.getSerialNumber())
        && Objects.equals(getModel(), that.getModel())
        && Objects.equals(getCapacityBytes(), that.getCapacityBytes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSerialNumber(), getModel(), getCapacityBytes());
  }

  @Override
  public String toString() {
    return "SmartDataRecord{"
        + "serialNumber='"
        + serialNumber
        + '\''
        + ", model='"
        + model
        + '\''
        + ", capacityBytes="
        + capacityBytes
        + '}';
  }
}

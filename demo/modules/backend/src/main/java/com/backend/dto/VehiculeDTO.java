package com.backend.dto;

public class VehiculeDTO extends BaseDTO{
  private String Number;
  private VehicleTypeDTO type;

  public String getNumber() {
    return Number;
  }

  public void setNumber(String number) {
    Number = number;
  }

  public VehicleTypeDTO getType() {
    return type;
  }

  public void setType(VehicleTypeDTO type) {
    this.type = type;
  }
}

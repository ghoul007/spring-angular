package com.backend.entity;


import javax.persistence.*;

@Entity
@Table(name="vehicule")
public class Vehicule  extends DistributedEntity{

  @Column(nullable = false)
  private String number;

  @ManyToOne
  @JoinColumn(name = "vehicletype", nullable = true)
  private VehiculeType type;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public VehiculeType getType() {
    return type;
  }

  public void setType(VehiculeType type) {
    this.type = type;
  }
}

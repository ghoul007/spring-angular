package com.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Vehicule  extends DistributedEntity{

  @Column(nullable = false)
  private String number;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}

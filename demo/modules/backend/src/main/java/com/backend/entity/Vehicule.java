package com.backend.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Vehicule")
public class Vehicule  extends DistributedEntity{

  @Column(nullable = false)
  private String number;

  @Column(nullable = true)
  private String hello;


  public String getHello() {
    return hello;
  }

  public void setHello(String hello) {
    this.hello = hello;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}

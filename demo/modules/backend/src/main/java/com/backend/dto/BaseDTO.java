package com.backend.dto;

import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

public abstract class BaseDTO {

  private Integer id;
  private LocalDateTime created;
  private LocalDateTime modified;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }

  @JsonIgnore
  public Boolean isNew() {
    return id == null;
  }

}

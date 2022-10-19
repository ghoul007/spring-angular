package com.backend.controller;

import com.backend.api.AbstractCRUDApi;
import com.backend.dto.BaseDTO;
import com.backend.entity.DistributedEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AbstractCRUDController<ENTITY extends DistributedEntity, DTO  extends BaseDTO> {
  private final AbstractCRUDApi<ENTITY,DTO> api;
  public AbstractCRUDController(final AbstractCRUDApi<ENTITY,DTO> api) {
    this.api = api;
  }

  @PostMapping
  public DTO save(@RequestBody DTO dto) {
    return api.save(dto);
  }

  @GetMapping("/{id}")
  public DTO getById(@PathVariable Integer id) {
    return api.getById(id);
  }

  @GetMapping("/list")
  public List<DTO> list() {
    return api.list();
  }

  @DeleteMapping("/{id}")
  public Boolean delete(@PathVariable Integer id) {
    return api.delete(id);
  }
}

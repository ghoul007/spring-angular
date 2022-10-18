package com.backend.api;

import com.backend.dto.BaseDTO;
import com.backend.entity.DistributedEntity;

import java.util.List;

public interface AbstractCRUDApi<Entity extends DistributedEntity, DTO extends BaseDTO> {

  DTO save(DTO dto);
  DTO getById(Integer id);
  List<DTO> list();
  Boolean delete(Integer id);
}

package com.backend.api;

import com.backend.dto.BaseDTO;
import com.backend.dto.search.PagedResponse;
import com.backend.dto.search.SearchRequest;
import com.backend.entity.DistributedEntity;

import java.util.List;

public interface AbstractCRUDApi<Entity extends DistributedEntity, DTO extends BaseDTO> {

  DTO save(DTO dto);
  DTO getById(Integer id);
  PagedResponse<DTO> list(SearchRequest request);
  Boolean delete(Integer id);
}

package com.backend.converter;

import com.backend.dto.BaseDTO;
import com.backend.entity.DistributedEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDTOConverter<Entity extends DistributedEntity, DTO extends BaseDTO> {

  public abstract DTO convert(final Entity entity);

  public void convert(final Entity entity, final DTO dto) {
    dto.setId(entity.getId());
    dto.setCreated(entity.getCreated());
    dto.setModified(entity.getModified());
  }

  public List<DTO> convertList(final List<Entity> entities){
    if(CollectionUtils.isEmpty(entities)){
      return Collections.emptyList();
    }

    return  entities.stream().sequential().map(this::convert).collect(Collectors.toList());
  }
}

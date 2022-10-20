package com.backend.converter;

import com.backend.dto.VehicleTypeDTO;
import com.backend.entity.VehiculeType;
import org.springframework.stereotype.Component;

@Component
public class VehicleTypeDTOConverter extends AbstractDTOConverter<VehiculeType, VehicleTypeDTO>  {
  @Override
  public VehicleTypeDTO convert(VehiculeType entity) {
    final VehicleTypeDTO dto = new VehicleTypeDTO();
    super.convert(entity, dto);

    dto.setName(entity.getName());

    return dto;
  }
}

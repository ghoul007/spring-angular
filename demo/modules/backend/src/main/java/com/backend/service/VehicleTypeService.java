package com.backend.service;

import com.backend.api.VehicleTypeApi;
import com.backend.converter.VehicleTypeDTOConverter;
import com.backend.dto.VehicleTypeDTO;
import com.backend.entity.VehiculeType;
import com.backend.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeService extends AbstractCRUDService<VehiculeType, VehicleTypeDTO> implements VehicleTypeApi {

  @Autowired
  public VehicleTypeService(final VehicleTypeRepository repository,
                            final VehicleTypeDTOConverter converter) {
    super(repository, converter);
  }

  @Override
  protected void updateEntity(VehiculeType entity, VehicleTypeDTO dto) {
    entity.setName(dto.getName());
  }

  @Override
  protected String getEntityTopic() {
    return "vehicletype";
  }
}
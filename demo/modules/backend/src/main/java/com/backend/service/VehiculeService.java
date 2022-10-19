package com.backend.service;

import com.backend.api.VehiculeApi;
import com.backend.converter.AbstractDTOConverter;
import com.backend.converter.VehiculeDTOConverter;
import com.backend.dto.VehiculeDTO;
import com.backend.entity.Vehicule;
import com.backend.repository.DistributedRepository;
import com.backend.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculeService extends AbstractCRUDService<Vehicule, VehiculeDTO> implements VehiculeApi {


  @Autowired
  public VehiculeService(final VehiculeRepository repository, final VehiculeDTOConverter converter) {
    super(repository, converter);
  }

  @Override
  protected String getEntityTopic() {
      return "vehicle";
  }

  @Override
  protected void updateEntity(Vehicule entity, VehiculeDTO dto) {
      entity.setNumber(dto.getNumber());
  }
}

package com.backend.converter;

import com.backend.dto.VehiculeDTO;
import com.backend.entity.Vehicule;
import org.springframework.stereotype.Component;

@Component
public class VehiculeDTOConverter extends AbstractDTOConverter<Vehicule, VehiculeDTO> {

  public  VehiculeDTO convert(Vehicule vehicule){

    final VehiculeDTO vehiculeDTO = new VehiculeDTO();
    super.convert(vehicule, vehiculeDTO);

    vehiculeDTO.setNumber(vehicule.getNumber());
    return vehiculeDTO;
  }
}

package com.backend.controller;

import com.backend.api.AbstractCRUDApi;
import com.backend.api.VehiculeApi;
import com.backend.dto.VehiculeDTO;
import com.backend.entity.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController extends AbstractCRUDController<Vehicule, VehiculeDTO> {

  @Autowired
  public VehiculeController(final VehiculeApi api) {
    super(api);
  }
}

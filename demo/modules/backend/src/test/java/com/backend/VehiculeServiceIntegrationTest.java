package com.backend;

import com.backend.dto.VehiculeDTO;
import com.backend.entity.Vehicule;
import com.backend.service.VehiculeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VehiculeServiceIntegrationTest {
  @Autowired
  public VehiculeService service;

  @Test
  void testVehiculeCRUD() {
    final VehiculeDTO dto = new VehiculeDTO();
    dto.setNumber("555");

    final VehiculeDTO savedVehicle = service.save(dto);

    assertThat(savedVehicle).isNotNull();
    assertThat(savedVehicle.getId()).isNotNull();
    assertThat(savedVehicle.getCreated()).isNotNull();
    assertThat(savedVehicle.getModified()).isNotNull();
    assertThat(savedVehicle.getNumber()).isEqualTo(dto.getNumber());


    final VehiculeDTO vehicleByID = service.getById(savedVehicle.getId());
    assertThat(vehicleByID).isNotNull();
    assertThat(vehicleByID)
      .extracting(
        VehiculeDTO::getId,
        VehiculeDTO::getNumber
      )
      .contains(
        savedVehicle.getId(),
        savedVehicle.getNumber()
      );

    final List<VehiculeDTO> vehicles = service.list();
    assertThat(vehicles).isNotNull();
    assertThat(vehicles).hasSize(1);


    savedVehicle.setNumber("new vehicle number");
    final VehiculeDTO updatedVehicle = service.save(savedVehicle);
    assertThat(updatedVehicle).isNotNull();
    assertThat(updatedVehicle.getNumber()).isEqualTo(savedVehicle.getNumber());
    assertThat(updatedVehicle.getModified()).isAfter(savedVehicle.getModified());

    final Boolean deleted = service.delete(savedVehicle.getId());
    assertThat(deleted).isTrue();
    assertThat(service.getById(savedVehicle.getId())).isNull();

    assertThat(service.delete(677774354)).isFalse();


  }

}

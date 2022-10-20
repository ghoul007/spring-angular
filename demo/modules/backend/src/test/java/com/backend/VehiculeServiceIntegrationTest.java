package com.backend;

import com.backend.dto.VehicleTypeDTO;
import com.backend.dto.VehiculeDTO;
import com.backend.dto.search.PagedResponse;
import com.backend.dto.search.SearchRequest;
import com.backend.entity.Vehicule;
import com.backend.repository.VehiculeRepository;
import com.backend.service.VehicleTypeService;
import com.backend.service.VehiculeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
public class VehiculeServiceIntegrationTest {
  @Autowired
  public VehiculeService service;

  private VehicleTypeDTO type;

  @Autowired
  public VehicleTypeService typeService;


  @Autowired
  public VehiculeRepository vehicleRepository;

  @BeforeEach
  public void setup() {
    VehicleTypeDTO dto = new VehicleTypeDTO();
    dto.setName("Vehicle type");

    type = typeService.save(dto);
  }


      @Test
    public void testVehicleNumberNull() {
        final VehiculeDTO dto = new VehiculeDTO();

        assertThatCode(() -> service.save(dto))
                .hasMessageContaining("not-null property references a null or transient value");
    }
    @Test
    public void testVehicleNumberUnique() {
        final VehiculeDTO dto = new VehiculeDTO();
        dto.setNumber("SBA - 1");

        assertThatCode(() -> service.save(dto)).doesNotThrowAnyException();
        assertThatCode(() -> service.save(dto)).hasMessageContaining("nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement");
    }

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

    final PagedResponse<VehiculeDTO> list = service.list(new SearchRequest());
    final List<VehiculeDTO> vehicles = list.getContent();
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


  @Test
  public void findModifiedSince() {
    final VehiculeDTO dto = new VehiculeDTO();
    dto.setType(type);

    // Create 3 vehicles
    dto.setNumber("V1");
    service.save(dto);

    dto.setNumber("V2");
    final VehiculeDTO v2 = service.save(dto);

    dto.setNumber("V3");
    service.save(dto);

    final PagedResponse<VehiculeDTO> list = service.list(new SearchRequest());
    assertThat(list.getContent())
      .extracting(VehiculeDTO::getNumber)
      .contains("V1", "V2", "V3");

    // Remember timestamp before modification
    final LocalDateTime timestampBeforeModification = LocalDateTime.now();

    // Modify second vehicle
    v2.setNumber("V2 - edited");
    service.save(v2);

    List<Vehicule> allModifiedSince = vehicleRepository.findAllModifiedSince(timestampBeforeModification);
    assertThat(allModifiedSince).hasSize(1);
    assertThat(allModifiedSince.get(0).getNumber()).isEqualTo(v2.getNumber());
  }

  @Test
  public void findByVehicleType() {
    VehicleTypeDTO t1 = new VehicleTypeDTO();
    t1.setName("T1");
    t1 = typeService.save(t1);

    VehicleTypeDTO t2 = new VehicleTypeDTO();
    t2.setName("T2");
    t2 = typeService.save(t2);

    VehiculeDTO v1 = new VehiculeDTO();
    v1.setType(t1);
    v1.setNumber("xx-1");
    v1 = service.save(v1);

    VehiculeDTO v2 = new VehiculeDTO();
    v2.setType(t2);
    v2.setNumber("xx-2");
    v2 = service.save(v2);

    final List<Vehicule> r1 = vehicleRepository.findAll(VehiculeRepository.Specs.byVehicleType(t1.getId()));
    final List<Vehicule> r2 = vehicleRepository.findAll(VehiculeRepository.Specs.byVehicleType(t2.getId()));

    assertThat(r1).hasSize(1);
    assertThat(r1).extracting(Vehicule::getId).containsExactly(v1.getId());

    assertThat(r2).hasSize(1);
    assertThat(r2).extracting(Vehicule::getId).containsExactly(v2.getId());
  }


}

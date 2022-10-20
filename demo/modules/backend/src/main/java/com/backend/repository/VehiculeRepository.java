package com.backend.repository;

import com.backend.entity.Vehicule;
import com.backend.entity.VehiculeType_;
import com.backend.entity.Vehicule_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends  DistributedRepository<Vehicule> {
  class Specs {
    public static Specification<Vehicule> byVehicleType(final Integer vehicleTypeId) {
      return (root, query, cb) -> cb.equal(
        root.get(Vehicule_.type).get(VehiculeType_.ID),
        vehicleTypeId
      );
    }
  }
}

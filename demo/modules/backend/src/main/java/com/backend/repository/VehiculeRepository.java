package com.backend.repository;

import com.backend.entity.Vehicule;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends  DistributedRepository<Vehicule> {
}

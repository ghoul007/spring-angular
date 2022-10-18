package com.backend.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class DistributedRepositoryImpl<Entity> extends SimpleJpaRepository<Entity, Integer> implements DistributedRepository<Entity>, JpaSpecificationExecutor<Entity> {


  public DistributedRepositoryImpl(final JpaEntityInformation<Entity, Integer> entityInformation, EntityManager em) {
    super(entityInformation, em);
  }
}

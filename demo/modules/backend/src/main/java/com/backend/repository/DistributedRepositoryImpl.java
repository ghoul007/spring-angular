package com.backend.repository;

import com.backend.entity.DistributedEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class DistributedRepositoryImpl<Entity> extends SimpleJpaRepository<Entity, Integer> implements DistributedRepository<Entity>, JpaSpecificationExecutor<Entity> {


  public DistributedRepositoryImpl(final JpaEntityInformation<Entity, Integer> entityInformation, EntityManager em) {
    super(entityInformation, em);
  }

  @Override
  public List<Entity> findAllModifiedSince(LocalDateTime time) {
    return super.findAll(modifiedSince(time));
  }

  @Override
  public List<Entity> findAll(final Specification<Entity> specification) {
    return super.findAll(specification);
  }

  private Specification<Entity> modifiedSince(final LocalDateTime timestamp) {
    return ((root, query, cb) -> cb.greaterThanOrEqualTo(
      root.get(DistributedEntity_.MODIFIED),
      timestamp
    ));
  }
}

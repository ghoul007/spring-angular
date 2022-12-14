package com.backend.service;

import com.backend.api.AbstractCRUDApi;
import com.backend.converter.AbstractDTOConverter;
import com.backend.dto.BaseDTO;
import com.backend.dto.search.PagedResponse;
import com.backend.dto.search.SearchRequest;
import com.backend.dto.search.util.SearchRequestUtil;
import com.backend.entity.DistributedEntity;
import com.backend.repository.DistributedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCRUDService<ENTITY extends DistributedEntity, DTO extends BaseDTO> implements AbstractCRUDApi<ENTITY, DTO> {


  private static final Logger LOG = LoggerFactory.getLogger(AbstractCRUDService.class);
  private DistributedRepository<ENTITY> repository;
  private AbstractDTOConverter<ENTITY, DTO> converter;
  private Class<ENTITY> entityClass;

  @Autowired
  private WebSocketService webSocketService;
 @Autowired
    private ErrorService errorService;


  protected abstract String getEntityTopic();

  public AbstractCRUDService(final DistributedRepository<ENTITY> repository,
                             final AbstractDTOConverter<ENTITY, DTO> converter) {
    this.repository = repository;
    this.converter = converter;


    final Class<?>[] params = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCRUDService.class);
    this.entityClass = (Class<ENTITY>) params[0];

  }
  protected abstract void updateEntity(final ENTITY entity, final DTO dto);

  @Override
  public DTO save(DTO dto) {
    final ENTITY entity;

    if (dto.isNew()) {
      entity = initEntity();
    } else {
      entity = findEntityById(dto.getId());
    }

    if (entity == null) {
      LOG.error("Failed to save entity of class '{}'", entityClass.getSimpleName());
      return null;
    }

    // Set modified timestamp
    entity.setModified(LocalDateTime.now());

    // Update entity
    updateEntity(entity, dto);

    // Save entity


    try {
      final ENTITY savedEntity = repository.save(entity);

      // Notify frontend that there has been a change on entity
      notifyFrontend();

      // Convert to DTO and return it
      return converter.convert(savedEntity);
    } catch (final Exception e) {
      errorService.displayError(e.getMessage());
      return null;
    }



    // Convert to DTO and return it
  }

  @Override
  public DTO getById(Integer id) {
    final ENTITY entity = findEntityById(id);
    if (entity == null) {
      LOG.error("Failed to find entity with ID '{}'", id);
      return null;
    }
    return converter.convert(entity);
  }

  @Override
  public PagedResponse<DTO> list(final SearchRequest request) {
    final Page<ENTITY> response = repository.findAll(SearchRequestUtil.toPageRequest(request));
    if (response.isEmpty()) {
      return new PagedResponse<>(Collections.emptyList(), 0, response.getTotalElements());
    }

    final List<DTO> dtos = getDtos(response.getContent());
    return new PagedResponse<>(dtos, dtos.size(), response.getTotalElements());
  }

  @Override
  public Boolean delete(Integer id) {
    final ENTITY entity = findEntityById(id);
    if (entity == null) {
      LOG.error("Failed to find entity with ID '{}'", id);
      return false;
    }
    try {
      repository.delete(entity);
      notifyFrontend();
      return true;
    } catch (final Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    }

  }

  public ENTITY findEntityById(final Integer id) {
    return repository.findById(id).orElse(null);
  }

  public List<DTO> getDtos(final List<ENTITY> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return Collections.emptyList();
    }

    return converter.convertList(entities);
  }


  private ENTITY initEntity() {
    try {
      final ENTITY entity = entityClass.newInstance();
      entity.setCreated(LocalDateTime.now());

      return entity;
    } catch (final Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    }
  }
  private void notifyFrontend() {
        final String entityTopic = getEntityTopic();
        if (entityTopic == null) {
            LOG.error("Failed to get entity topic");
            return;
        }

        webSocketService.sendMessage(entityTopic);
    }

}

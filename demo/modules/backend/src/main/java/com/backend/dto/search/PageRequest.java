package com.backend.dto.search;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequest extends AbstractPageRequest {
  /**
   * Creates a new {@link AbstractPageRequest}. Pages are zero indexed, thus providing 0 for {@code page} will return
   * the first page.
   *
   * @param page must not be less than zero.
   * @param size must not be less than one.
   */
  public PageRequest(int page, int size) {
    super(page, size);
  }

  @Override
  public Pageable next() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Pageable previous() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Pageable first() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Pageable withPage(int pageNumber) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Sort getSort() {
    return Sort.unsorted();
  }


}

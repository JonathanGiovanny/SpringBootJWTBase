package com.jjo.h2.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.exception.Either;
import com.jjo.h2.model.H;

public interface HService {
  
  Optional<H> findById(Long id);

  H saveH(H h);

  Boolean isUrlAvailable(Long id, String url);

  H updateH(Long id, H h);

  /**
   * Get all the H records based on the filter
   * 
   * @param filter
   * @param pageable
   * @return
   */
  Page<H> findAll(H filter, Pageable pageable);

  Page<H> findAll(Pageable pageable);

  void deleteH(Long id);

  Either<?, H> increaseClick(Long id);
}

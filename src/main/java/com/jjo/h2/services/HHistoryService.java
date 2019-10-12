package com.jjo.h2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;

public interface HHistoryService {

  /**
   * Get all the HHistory based on the paged request
   * 
   * @param pageable
   * @return
   */
  Page<HHistory> getAll(Pageable pageable);

  /**
   * Save the History record
   * 
   * @param hHistory
   * @return
   */
  HHistory save(H entity);
}

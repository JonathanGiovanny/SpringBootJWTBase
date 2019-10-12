package com.jjo.h2.services;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HHistoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HHistoryServiceImpl implements HHistoryService {

  private final @NonNull HHistoryRepository hHistoryRepo;

  @Override
  public Page<HHistory> getAll(Pageable pageable) {
    return this.hHistoryRepo.findAll(pageable);
  }

  @Override
  public HHistory save(H entity) {
    return hHistoryRepo.save(buildHistory(entity));
  }

  private HHistory buildHistory(H entity) {
    return HHistory.builder()
        .h(entity)
        .date(LocalDateTime.now())
        .build();
  }
}

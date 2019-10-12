package com.jjo.h2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jjo.h2.model.HHistory;

public interface HHistoryRepository extends JpaRepository<HHistory, Long> {

}

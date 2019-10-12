package com.jjo.h2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.jjo.h2.model.H;

public interface HRepository extends JpaRepository<H, Long>, QuerydslPredicateExecutor<H>, HRepositoryCustom {

  Optional<H> findByUrlIgnoreCase(String url); 
}

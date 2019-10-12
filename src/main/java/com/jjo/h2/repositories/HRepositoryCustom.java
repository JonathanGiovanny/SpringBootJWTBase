package com.jjo.h2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.jjo.h2.model.H;

public interface HRepositoryCustom extends QuerydslPredicateExecutor<H> {

  Page<H> filter(H filter, Pageable pageable);
}

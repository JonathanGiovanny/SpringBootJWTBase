package com.jjo.h2.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jjo.h2.model.HType;

public interface HTypeRepository extends JpaRepository<HType, Integer> {

  Optional<HType> findByNameIgnoreCase(String name);

  @Query(value = "SELECT ht FROM HType ht WHERE LOWER(ht.name) LIKE LOWER(:name)")
  Page<HType> findByNameLike(@Param(value = "name") String name, Pageable pageable);
}

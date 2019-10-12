package com.jjo.h2.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jjo.h2.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Long> {

  Optional<Tags> findByNameIgnoreCase(String name);
  
  Page<Tags> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
